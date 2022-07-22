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
        self.herencia = []
    
    def __get_list_herencia_json(self):
        list_herencia_json = []
        for herencia in self.herencia:
            list_herencia_json.append(herencia.to_json())
        return list_herencia_json

    def to_json(self):
        return {
            "nombre": self.nombre,
            "pathModule": self.path_module,
            "herencia": self.__get_list_herencia_json()
        }
    
    def __str__(self) -> str:
        return str(self.to_json())


class ArchivoPython:
    def __init__(self, file, name):
        self.file = file
        self.name = name
        self.clases = []

    def __get_list_classes_json(self):
        list_classes_json = []
        for clase in self.clases:
            list_classes_json.append(clase.to_json())
        return list_classes_json

    def to_json(self):
        return {
            "ficheroStr": self.file,
            "module": self.name,
            "clases": self.__get_list_classes_json()
        }
    
    def __str__(self) -> str:
        return str(self.to_json())

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

    def push_folder(self, folder, folder_level):
        if len(folder_level) == 1:
            self.directorios.append(folder)
        else:
            for subfolder in self.directorios:
                if subfolder.name == folder_level[0]:
                    del folder_level[0]
                    subfolder.push_folder(folder, folder_level)

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
    
    def __get_list_files_json(self):
        list_files = []
        for archivo in self.archivos:
            list_files.append(archivo.to_json())
        return list_files

    def __get_list_folders_json(self):
        list_folders = []
        for directorio in self.directorios:
            list_folders.append(directorio.to_json())
        return list_folders

    def to_json(self):
        return {
            "ficheroStr": self.directorio,
            "archivos": self.__get_list_files_json(),
            "directorios": self.__get_list_folders_json()
        }

    def __str__(self) -> str:
        return str(self.to_json())

#Obtiene el archivo las lineas de inicio y fin de una clase
def get_lines_class(class_name):
    file = inspect.getsourcefile(class_name)
    codigo, index = inspect.findsource(class_name)
    index_last = index
    for ind_linea in range(index+1, len(codigo)):
        if codigo[ind_linea].startswith('class '):
            break
        index_last += 1
    data = {'inicio': str(index), 'fin': str(index_last), 'archivo': str(file).replace("'", ""), 'clase': class_name.__name__}
    print("get_lines_class:"+json.dumps(json.loads(str(data).replace("'", '"'))))

#List all python files in current folder and subdirectories and save in a list
def list_files(path):
    files = []
    dir_names = []
    for root, dirs, filenames in os.walk(path):
        dir_names += ['{}{}{}'.format(root, os.sep, dir_name) for dir_name in dirs if dir_name != "__pycache__"]
        for filename in filenames:
            if filename.endswith(".py") and filename != "__init__.py" and filename != "scan.py":
                files.append(os.path.join(root, filename))
    return [files, dir_names]

def list_all_instancias(local_val: dict):
    classes = [cls for cls in inspect.getmembers(sys.modules[__name__], inspect.isclass) if cls[1].__module__ != '__main__']
    classes_values = []
    for key, value in local_val.items():
        for cls in classes:
            if isinstance(value, cls[1]):
                attr_value = {'attrs': [], 'methods': [], 'name_class': cls[0], 'name': key}
                # attribute is a string representing the attribute name
                for attribute in dir(local_val[key]):
                    # Get the attribute value
                    attribute_value = getattr(local_val[key], attribute)
                    # Check that it is callable
                    if callable(attribute_value):
                        # Filter all dunder (__ prefix) methods
                        if attribute.startswith('__') == False:
                            attr_value['methods'].append({
                                'name': attribute,
                                'args': [arg for arg in inspect.getfullargspec(attribute_value).args if arg != 'self']
                            })
                for atkey in local_val[key].__dict__.keys():
                    attr_value['attrs'].append({
                        'key': atkey,
                        'value': str(local_val[key].__dict__[atkey]),
                        'type': str(type(local_val[key].__dict__[atkey])).replace("'", "")
                    })
                classes_values.append(attr_value)
                break

    print("list_all_instancias:"+json.dumps(json.loads(str(classes_values).replace("'", '"'))))


def list_all_python_class_with_hierarchy(list_of_files_folder):
    list_of_files = list_of_files_folder[0]
    
    #Almacena errores que se encuentren al importar un modulo[str]
    list_errores = []
    #Almacena las rutas de los directorios[str]
    list_folders_str = []
    #Almacena las rutas de las clases[str]
    list_class_path = []
    #Almacena los directorios[Directorio]
    dict_folders_class = {}
    for file in list_of_files:
        ## Eliminar modulos previamente importados.
        exec('if "{0}" in sys.modules.keys():del sys.modules["{0}"]'.format(file[:-3].replace(os.sep, ".")))

        folder = os.sep.join(file.split(os.sep)[:-1])
        #Si no esta el folder se crea una instancia
        if folder not in list_folders_str:
            list_folders_str.append(folder)
            dict_folders_class[folder] = Directorio(folder)

        directorio = dict_folders_class[folder]
        module = file.split(os.sep)[-1].split(".")[0]
        path_module_file = file[:-3].replace(os.sep, ".")
        archivo = directorio.get_file_or_create(module, path_module_file)

        try:
            for class_name, class_type in inspect.getmembers(importlib.import_module(file[:-3].replace(os.sep, ".")), inspect.isclass):
                path_module_class = ".".join(str(class_type).split("'")[1].split(".")[:-1])

                #Verifica que la clase sea del mismo archivo y no de otro debido al import
                if(path_module_file == path_module_class):
                    clase_path = path_module_class + "." + class_name
                    if clase_path not in list_class_path:
                        list_class_path.append(clase_path)
                        clase_python = ClasePython(class_name, path_module_class)

                        #Verifica que la clase tenga mas de una clase base, debido a que por default cuando no hay clase base se lista ka object class
                        class_bases = class_type.__bases__
                        if len(class_bases) != 1 or class_bases[0].__name__ != "object":
                            for type_class in class_bases:
                                class_split = str(type_class).split("'")[1].split(".")
                                path_mod_herencia, class_name_herencia = ".".join(class_split[:-1]), class_split[-1]

                                clase_python.herencia.append(ClasePython(class_name_herencia, path_mod_herencia))
                        archivo.clases.append(clase_python)
        except Exception as err:
            list_errores.append(str(err))
    return [dict_folders_class, list_errores]

def scanner_project():
    dict_folders_class = list_all_python_class_with_hierarchy(list_files("src"))
    errores = dict_folders_class[1]
    if errores:
        for error in errores:
            print("errores_importacion:{}".format(error))
    dict_folders_class = dict_folders_class[0]
    src = dict_folders_class["src"]
    del dict_folders_class["src"]
    for val in dict_folders_class.values():
        src.push_folder(val, val.directorio.split(os.sep)[1:])
    src.set_absolute_path(os.getcwd())
    module_names = src.get_names_modules()
    import_modules = []
    for module in module_names:
        import_modules.append("from {} import *".format(module))
    #print("get_directorio_trabajo:"+json.dumps(json.loads(str(src).replace("'", '"')), indent=4))
    print("get_directorio_trabajo:"+json.dumps(json.loads(str(src).replace("'", '"'))))
    print("import_modules:"+json.dumps(json.loads(str(import_modules).replace("'", '"'))))

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
