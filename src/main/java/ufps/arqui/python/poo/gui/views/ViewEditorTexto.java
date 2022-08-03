/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.arqui.python.poo.gui.views;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.concurrent.Task;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;
import ufps.arqui.python.poo.gui.MainApp;
import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.models.ArchivoPython;
import ufps.arqui.python.poo.gui.utils.BluePyUtilities;

/**
 *
 * @author dunke
 */
public class ViewEditorTexto extends ViewBase<BorderPane, ArchivoPython>{
    private TabPane tabPane;
    private Map<ArchivoPython, Tab> tabs = new HashMap<>();

    public ViewEditorTexto() {
        super();
    }
    
    @Override
    public void initialize() {
        super.initialize(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void preload(ArchivoPython object) {
        try{
            Tab tab = this.tabs.get(object);
            if(tab == null){
                CustomCodeArea codeArea = new CustomCodeArea(object);
                tab = new Tab(object.getFichero().getName(), new VirtualizedScrollPane<>(codeArea.getCodeArea()));
                this.tabs.put(object, tab);
                this.tabPane.getTabs().add(tab);
            }
            this.tabPane.getSelectionModel().select(tab);
            MainApp.getStageView(BluePyUtilities.VIEW_EDITOR_TEXTO).requestFocus();
        }catch(Exceptions e){
            e.printStackTrace();
        }
    }
    
    private static class ManageTextEditor{
        private static final ExecutorService executor = Executors.newSingleThreadExecutor();
        
        private static final String[] KEYWORDS = new String[] {
            "False", "None", "True", "__peg_parser__", "and", 
            "as", "assert", "async", "await", "break", "class", 
            "continue", "def", "del", "elif", "else", "except", 
            "finally", "for", "from", "global", "if", "import", 
            "in", "is", "lambda", "nonlocal", "not", "or", "pass", 
            "raise", "return", "try", "while", "with", "yield"
        };

        private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
        private static final String PAREN_PATTERN = "\\(|\\)";
        private static final String BRACE_PATTERN = "\\{|\\}";
        private static final String BRACKET_PATTERN = "\\[|\\]";
        private static final String SEMICOLON_PATTERN = "\\;";
        private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
        private static final String COMMENT_PATTERN = "#(\\S)*";

        private static final Pattern PATTERN = Pattern.compile(
                "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                + "|(?<PAREN>" + PAREN_PATTERN + ")"
                + "|(?<BRACE>" + BRACE_PATTERN + ")"
                + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                + "|(?<STRING>" + STRING_PATTERN + ")"
                + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
        );
        
        private static StyleSpans<Collection<String>> computeHighlighting(String text) {
            Matcher matcher = PATTERN.matcher(text);
            int lastKwEnd = 0;
            StyleSpansBuilder<Collection<String>> spansBuilder
                    = new StyleSpansBuilder<>();
            while(matcher.find()) {
                String styleClass =
                        matcher.group("KEYWORD") != null ? "keyword" :
                        matcher.group("PAREN") != null ? "paren" :
                        matcher.group("BRACE") != null ? "brace" :
                        matcher.group("BRACKET") != null ? "bracket" :
                        matcher.group("SEMICOLON") != null ? "semicolon" :
                        matcher.group("STRING") != null ? "string" :
                        matcher.group("COMMENT") != null ? "comment" :
                        null; /* never happens */ assert styleClass != null;
                spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
                spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
                lastKwEnd = matcher.end();
            }
            spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
            return spansBuilder.create();
        }

        public static ExecutorService getExecutor() {
            return executor;
        }
    }
    
    private class CustomCodeArea{
        private CodeArea codeArea;
        private Subscription subscription;
        private ExecutorService executor;
        private ArchivoPython archivoPython;

        public CustomCodeArea(ArchivoPython archivoPython) throws Exceptions {
            this.codeArea = new CodeArea(archivoPython.getContenido());
            this.executor = ManageTextEditor.getExecutor();
            this.archivoPython = archivoPython;
            
            this.init();
        }
        
        private void init(){
            this.codeArea.setParagraphGraphicFactory(LineNumberFactory.get(this.codeArea));
            this.subscription = this.codeArea.multiPlainChanges()
                    .successionEnds(Duration.ofMillis(500))
                    .retainLatestUntilLater(executor)
                    .supplyTask(this::computeHighlightingAsync)
                    .awaitLatest(this.codeArea.multiPlainChanges())
                    .filterMap(t -> {
                        if(t.isSuccess()) {
                            return Optional.of(t.get());
                        } else {
                            t.getFailure().printStackTrace();
                            return Optional.empty();
                        }
                    })
                    .subscribe(this::applyHighlighting);

            // call when no longer need it: `cleanupWhenFinished.unsubscribe();`
        }
        
        private Task<StyleSpans<Collection<String>>> computeHighlightingAsync() {
            String text = this.codeArea.getText();
            Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
                @Override
                protected StyleSpans<Collection<String>> call() throws Exception {
                    return ManageTextEditor.computeHighlighting(text);
                }
            };
            executor.execute(task);
            return task;
        }

        private void applyHighlighting(StyleSpans<Collection<String>> highlighting) {
            this.codeArea.setStyleSpans(0, highlighting);
        }

        public CodeArea getCodeArea() {
            return codeArea;
        }
    }
}
