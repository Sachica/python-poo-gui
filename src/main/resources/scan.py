import importlib
import inspect
import json
import os
import sys
import traceback


class ClasePython:
    def __init__(self, nombre, path_module):
        self.nombre = nombre
        self.path_module = path_module
        self.bases = []

    def to_json(self):
        return {
            "name": self.nombre,
            "pathModule": self.path_module,
            "bases": [str(x) for x in self.bases]
        }
    
    def __str__(self) -> str:
        return self.path_module +'.'+ self.nombre

class ArchivoPython:
    def __init__(self, file, name):
        self.file = file
        self.name = name
        self.clases = []

    def to_json(self):
        return {
            "ficheroStr": self.file,
            "module": self.name,
            "classes": [str(x) for x in self.clases]
        }

    def __str__(self) -> str:
        return self.file

class Directorio:
    def __init__(self, directorio):
        self.directorio = directorio
        self.name = directorio.split(os.sep)[-1]
        self.archivos = []
        self.directorios = []
    
    def set_absolute_path(self, absolute_path):
        self.directorio = absolute_path + os.sep + self.directorio
        for file in self.archivos:
            file.file = self.directorio + os.sep + file.file + ".py"

        for folder in self.directorios:
            folder.set_absolute_path(absolute_path)

    def get_names_modules(self):
        list_mudules = []
        for file in self.archivos:
            list_mudules.append(file.name)

        for folder in self.directorios:
            list_mudules += folder.get_names_modules()
        return list_mudules

    def append_folder(self, folder):
        self.directorios.append(folder)

    def get_file_or_create(self, file, name):
        archivo = self.get_file(file)
        if archivo == None:
            archivo = ArchivoPython(file, name)
            self.archivos.append(archivo)
        return archivo

    def get_file(self, name):
        for archivo in self.archivos:
            if archivo.file == name:
                return archivo
        return None

    def to_json(self):
        return {
            "ficheroStr": self.directorio,
            "files": [str(x) for x in self.archivos],
            "directorys": [str(x) for x in self.directorios]
        }

    def __str__(self) -> str:
        return self.directorio  

class AttrInstancia:
    def __init__(self, key, value, type, to_reference):
        self.key = key
        self.value = value
        self.type = type
        #<class 'clzz'> -> clzz
        self.rawtype = type.split("'")[1].split(".")[-1]
        self.to_reference = to_reference
        self.owner_class = None

    def update_owner(self, owner_class):
        self.owner_class = owner_class

    def to_json(self):
        return {
            "key": self.key,
            "value": str(self.value),
            "type": self.type,
            "rawType": self.rawtype,
            "toReference": self.to_reference,
            "owner": self.owner_class
        }
    
    def __str__(self):
        return str(self.key)

class MethodInstancia:
    def __init__(self, name, args, docs):
        self.name = name
        self.args = args
        self.docs = docs

    def update_owner(self, owner_class):
        self.owner_class = owner_class

    def to_json(self):
        return {
            "name": self.name,
            "args": self.args,
            "docs": self.docs,
            "owner": self.owner_class
        }

    def __str__(self):
        return str(self.name)

class MundoInstancia:
    def __init__(self, obj, name_var, name_class, iscollection):
        self.obj = obj
        self.id = id(obj)
        self.name = name_var
        self.isdeclared = name_var != None
        #<class 'classes.A'> -> A
        self.name_class = name_class.split("'")[1].split(".")[-1]
        self.attrs = []
        self.methods = []
        self.iscollection = iscollection
        self.collection_values = []

    def append_attr(self, attr):
        attr.update_owner(self.name_class)
        self.attrs.append(attr)

    def append_method(self, method):
        method.update_owner(self.name_class)
        self.methods.append(method)
    
    def set_collect_value(self, collection_values):
        self.collection_values = collection_values

    def __get_members_by_class(self, list_members):
        res = {}
        for x in list_members:
            if not x.owner_class in res:
                res[x.owner_class] = []
            res[x.owner_class].append(x.to_json())
        return res

    def to_json(self):
        return {
            'id': str(self.id),
            'name': self.name if self.name else "-",
            'name_class': self.name_class,
            'attrs': self.__get_members_by_class(self.attrs),
            'methods': self.__get_members_by_class(self.methods),
            'isDeclared': self.isdeclared,
            'isCollection': self.iscollection,
            'collectionValues': [x.to_json() for x in self.collection_values]
        }
    
    def __str__(self):
        return str(self.name)

#Obtiene el archivo las lineas de inicio y fin de una clase
def get_lines_class(class_name):
    file = inspect.getsourcefile(class_name)
    codigo, index = inspect.findsource(class_name)
    index_last = index
    for ind_linea in range(index+1, len(codigo)):
        if codigo[ind_linea].startswith('class '):
            break
        index_last += 1
    data = {'inicio': str(index), 'fin': str(index_last), 'archivo': str(file), 'clase': class_name.__name__}
    print("get_lines_class:"+json.dumps(data))

def list_files_and_get_root_directory(path):
    dict_directory = {}
    dict_files = {}
    for root, dirs, filenames in os.walk(path):
        if not "__pycache__" in root:
            if root not in dict_directory:
                dict_directory[root] = Directorio(root)
            directory = dict_directory[root]

            for filename in filenames:
                if filename.endswith(".py") and filename != "__init__.py" and filename != "scan.py":
                    module = filename.split('.')[0]
                    path_module = '.'.join([root.replace(os.sep, '.'), module])

                    dict_files[path_module] = directory.get_file_or_create(module, path_module)

            
            for dir_name in dirs:
                if dir_name != "__pycache__":
                    dir_path = os.path.join(root, dir_name)

                    sub_dir = Directorio(dir_path)
                    directory.append_folder(sub_dir)
                    dict_directory[dir_path] = sub_dir
    return [dict_directory, dict_files]

def list_all_instancias():
    local_val = globals()

    #Lista todas las clases pertenecientes al proyecto
    classes = [cls for cls in inspect.getmembers(sys.modules[__name__], inspect.isclass) if cls[1].__module__ != '__main__']

    list_ids = []
    dict_instances = {}
    for key, value in local_val.items():
        for cls in classes:
            #Valida si el objeto es una instancia de alguna de las clases listadas
            #ademas valida que pertenezcan a la misma clase ya que por ejemplo
            #si la instancia[value] es subclase de [cls[1]] retornara True
            if isinstance(value, cls[1]) and value.__class__ == cls[1]:
                if id(value) not in list_ids:
                    list_ids.append(id(value))
                    attr_value = inspect_and_get_instance_from(key, value, dict_instances, list_ids, classes)
                    dict_instances[str(id(value))] = attr_value
                else:
                    id_str = str(id(value))
                    dict_instances[id_str].isdeclared = True
                    dict_instances[id_str].name = key
                break
    apply_owner_base_classes(dict_instances)
    list_instances = {k: v.to_json() for k, v in dict_instances.items()}
    #print(json.dumps(list_instances, indent=4))
    print("list_all_instancias:"+json.dumps(list_instances))

#Aplica los propietarios a los atributos y metodos correspondientes
#en caso de herencia
def apply_owner_base_classes(dict_instances):
    for instance in dict_instances.values():
        dict_members = {}
        get_members_by_class(instance.obj, dict_members)
        for class_owner, info in dict_members.items():
            #<class 'classes.A'> -> A
            class_owner = class_owner.split("'")[1].split(".")[-1]
            for attrib in instance.attrs:
                if attrib.key in info["attribs"]:
                    attrib.update_owner(class_owner)

            for method in instance.methods:
                if method.name in info["methods"]:
                    method.update_owner(class_owner)

#Obtiene los atributos y metodos por clase
def get_members_by_class(obj, dict_members):
    typecls = type(obj)
    #Almecenara todos los metodos heredados
    diff_methods = set()
    #Almecenara todos los atributos heredados
    diff_attribs = set()

    dict_members[str(typecls)] = {}
    dict_members[str(typecls)]["bases"] = set()

    for base_class in typecls.__bases__:
        ins_base_class = base_class()
        diff_methods = diff_methods.union(set(dir(base_class)))

        #Las colecciones de python y la clase object no cuentan con atributos        
        if not iscollection(typecls) and base_class != object:
            diff_attribs = diff_attribs.union(set(ins_base_class.__dict__.keys()))

        dict_members[str(typecls)]["bases"].add(str(base_class))
        #Llamada recuriva para obtener super clases de la clase base actual
        get_members_by_class(ins_base_class, dict_members)

    #Las colecciones de python y la clase object no cuentan con atributos        
    if not iscollection(obj) and typecls != object:
        raw_attribs = set(obj.__dict__.keys())
        self_attribs = raw_attribs.difference(diff_attribs)
        dict_members[str(typecls)]["attribs"] = list(self_attribs)

        #Si una clase ya hereda de otra no se lista la clase object de la cual heradan todas
        #las clases, por lo tanto se agrega de forma manual
        dict_members[str(typecls)]["bases"].add(str(object))
    else:
        dict_members[str(typecls)]["attribs"] = []

    raw_methods = set(dir(typecls))
    obj_methods = set(dir(object))
    self_methods = raw_methods.difference(diff_methods).difference(set(['__module__', '__weakref__']))

    #Si la clase actual es 'object' sus metodos seran eliminados
    #dado que, set(dir(object)).difference(set(dir(object))) = ()
    members = list(self_methods.difference(obj_methods)) if typecls != object else list(obj_methods)
    dict_members[str(typecls)]["methods"] = members

def inspect_and_get_instance_from(name, obj, dict_instances, list_ids, classes):
    attr_value = MundoInstancia(obj, name, str(type(obj)), iscollection(obj))
    
    #Inspección de metodos
    inspect_and_save_methods(obj, attr_value)

    #Inspección de atributos
    for atkey in obj.__dict__.keys():
        value = obj.__dict__[atkey]
        typeval = type(value)

        iscollect = iscollection(value)
        isfromproject = isinstance_from_class_project(value, classes)
        isregistered = id(value) in list_ids
        isreference = iscollect or isfromproject or isregistered

        if isfromproject:
            if not isregistered:
                strategy_for_classproject_or_collection(value, dict_instances, list_ids, classes)
            value = id(value)

        elif iscollect:
            if not isregistered:
                strategy_for_classproject_or_collection(value, dict_instances, list_ids, classes)
            value = id(value)

        attr_value.append_attr(AttrInstancia(
            atkey,
            value,
            str(typeval),
            isreference
        ))

    return attr_value

def inspect_and_save_methods(obj, instance):
    # attribute is a string representing the attribute name
    for attribute in dir(obj):
        # Get the attribute value
        attribute_value = getattr(obj, attribute)
        # Check that it is callable
        if callable(attribute_value):
            args = []
            #Algunas funciones lanzan excepcion cuando se examinan sus atributos
            try:
                args = [arg for arg in inspect.getfullargspec(attribute_value).args if arg != 'self']
            except:
                pass
            instance.append_method(MethodInstancia(
                attribute,
                args,
                attribute_value.__doc__
            ))

#Itera una python collection 'obj' en busca de instancias de clases del proyecto
def inspect_for_collection(obj, dict_instances, list_ids, classes):
    if isinstance(obj, list) or isinstance(obj, tuple) or isinstance(obj, set):
        res = []
        i = 0
        for x in obj:
            approp_value = inspect_and_get_apropiate_value(x, dict_instances, list_ids, classes)
            attr = AttrInstancia(str(i), approp_value, str(type(x)), x != approp_value)
            res.append(attr)
            i += 1
        return res

    if isinstance(obj, dict):
        res = []
        for k, v in obj.items():
            approp_value = inspect_and_get_apropiate_value(v, dict_instances, list_ids, classes)
            attr = AttrInstancia(k, approp_value, str(type(v)), v != approp_value)
            res.append(attr)
        return res

#Inspecciona si el valor es una instancia del proyecto o es una python collection, en ese
#caso se tratara el objeto y retornara el id de referencia, en caso contrario el valor
#es primitivo
def inspect_and_get_apropiate_value(x, dict_instances, list_ids, classes):
    isfromproject = isinstance_from_class_project(x, classes)
    iscollect = iscollection(x)
    if isfromproject or iscollect:
        if id(x) not in list_ids:
            strategy_for_classproject_or_collection(x, dict_instances, list_ids, classes)
        return id(x)
    return x

#Realiza un tratamiento al objeto 'x' en funcion de si es una python collection o
#instancia de alguna clase del proyecto
def strategy_for_classproject_or_collection(x, dict_instances, list_ids, classes):
    attr_instance = None
    list_ids.append(id(x))

    if iscollection(x):
        attr_instance = MundoInstancia(x, None, str(type(x)), True)
        inspect_and_save_methods(x, attr_instance)
        attr_instance.set_collect_value(inspect_for_collection(x, dict_instances, list_ids, classes))
    else:
        attr_instance = inspect_and_get_instance_from(None, x, dict_instances, list_ids, classes)

    dict_instances[str(id(x))] =  attr_instance

#Valida si el objeto 'obj' es una instancia de alguna clase del proyecto
def isinstance_from_class_project(obj, classes):
    for _, clzztype in classes:
        if type(obj) == clzztype:
            return True
    return False 

#Valida si el objeto 'obj' es una instancia de una python collection
def iscollection(obj):
    return isinstance(obj, list) or isinstance(obj, dict) or isinstance(obj, tuple) or isinstance(obj, set)

def list_all_python_class_and_errors(folder_root: Directorio):
    #Almacena errores que se encuentren al importar un modulo[str]
    list_errores = []

    #Almacena los objetos ClasePython para guardar para en caso de herencia 
    #se tenga referencia al mismo objeto/clase
    dict_class_classpython = {}

    #Pila por la cual se recorre iterativamente el directorio raiz y subdirectorios
    stack_folder = [folder_root]

    while len(stack_folder) > 0:
        directorio = stack_folder.pop()

        for archivo in directorio.archivos:
            # Eliminar modulos previamente importados.
            if archivo.name in sys.modules.keys():
                del sys.modules[archivo.name]

            try:
                for class_name, class_type in inspect.getmembers(importlib.import_module(archivo.name), inspect.isclass):
                    path_module_class = ".".join(str(class_type).split("'")[1].split(".")[:-1])

                    #Verifica que la clase sea del mismo archivo y no de otro debido al import
                    if(archivo.name == path_module_class):

                        #Valida si la clase ya se encuentra instanciada y la añade en el archivo correspondiente
                        clase_path = path_module_class + "." + class_name
                        if clase_path not in dict_class_classpython:
                            dict_class_classpython[clase_path] = ClasePython(class_name, path_module_class)
                            archivo.clases.append(dict_class_classpython[clase_path])
                        clase_python = dict_class_classpython[clase_path]

                        #Verifica que la clase tenga mas de una clase base
                        #debido a que por default cuando no hay clase base se lista la object class
                        class_bases = class_type.__bases__
                        if len(class_bases) != 1 or class_bases[0].__name__ != "object":
                            for type_class in class_bases:
                                class_split = str(type_class).split("'")[1].split(".")
                                path_mod_herencia, class_name_herencia = ".".join(class_split[:-1]), class_split[-1]
                                
                                #Valida si la clase base ya se encuentra instanciada
                                class_path_base = '.'.join([path_mod_herencia, class_name_herencia])
                                if class_path_base not in dict_class_classpython:
                                    dict_class_classpython[class_path_base] = ClasePython(class_name_herencia, path_mod_herencia)

                                clase_python.bases.append(dict_class_classpython[class_path_base])
            except Exception as err:
                list_errores.append(str(err))
        for sub_dir in directorio.directorios:
            stack_folder.append(sub_dir)
    return [dict_class_classpython.values(), list_errores]

def scanner_project():
    dict_directorys, dict_files = list_files_and_get_root_directory("src")
    src = dict_directorys["src"]
    class_and_errors = list_all_python_class_and_errors(src)

    classes_obj = class_and_errors[0]
    errores = class_and_errors[1]

    if errores:
        for error in errores:
            print("errores_importacion:{}".format(error))

    #Cambia la ruta relativa a absoluta
    src.set_absolute_path(os.getcwd())

    #Preparar imports modulos para print
    module_names = src.get_names_modules()
    import_modules = []
    for module in module_names:
        import_modules.append("from {} import *".format(module))

    #Clases python objetos a json
    classes_str = {}
    for clazz in classes_obj:
        classes_str[str(clazz)] = clazz.to_json()

    #Archivos python objetos a json
    archivos_str = {}
    for archivos in dict_files.values():
        archivos_str[str(archivos)] = archivos.to_json()

    #Directorios objetos a json
    directorys_str = {}
    for directorio in dict_directorys.values():
        directorys_str[str(directorio)] = directorio.to_json()
    
    data_json = { 
        "classes": classes_str, 
        "files": archivos_str,
        "directorys": directorys_str
    }
    
    print('get_directorio_trabajo:'+json.dumps(data_json))
    print("import_modules:"+json.dumps(import_modules))

def compile_file(pathfile):
    data = None
    file = open(pathfile, "r")
    file_read = file.read()
    file.close()
    
    try:
        exec(file_read)
    except Exception as e:
        out_ex = traceback.format_exception(*sys.exc_info())[2:]
        lineno = int(out_ex[0].split(',')[1].replace(' line ', ""))
        error = out_ex[-1].rstrip()
        problem = ""
        with open(pathfile, "r") as file:
            n = 1
            for line in file:
                if n == lineno:
                    problem = line.rstrip()
                n += 1
        data = {'nlinea': lineno, 'error': error, 'problema': problem}
    if data:
        print("error_compile:"+json.dumps(data))
    else:
        print("error_compile:")
