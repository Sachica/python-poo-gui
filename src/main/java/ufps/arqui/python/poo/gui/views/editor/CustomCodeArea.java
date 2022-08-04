/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.arqui.python.poo.gui.views.editor;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import javafx.concurrent.Task;
import javafx.scene.control.Tab;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.reactfx.Subscription;
import ufps.arqui.python.poo.gui.exceptions.Exceptions;
import ufps.arqui.python.poo.gui.models.ArchivoPython;

/**
 *
 * @author dunke
 */
public class CustomCodeArea {

    private CodeArea codeArea;
    private Subscription subscription;
    private ExecutorService executor;
    private ArchivoPython archivoPython;
    private Tab tab;

    public CustomCodeArea(ArchivoPython archivoPython, Tab tab) throws Exceptions {
        this.archivoPython = archivoPython;
        this.tab = tab;
        
        this.codeArea = new CodeArea();
        this.executor = ManageTextEditor.getExecutor();

        this.init();
    }

    private void init() throws Exceptions {
        this.codeArea.setParagraphGraphicFactory(LineNumberFactory.get(this.codeArea));
        this.subscription = this.codeArea.multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .retainLatestUntilLater(executor)
                .supplyTask(this::computeHighlightingAsync)
                .awaitLatest(this.codeArea.multiPlainChanges())
                .filterMap(t -> {
                    if (t.isSuccess()) {
                        return Optional.of(t.get());
                    } else {
                        t.getFailure().printStackTrace();
                        return Optional.empty();
                    }
                })
                .subscribe(this::applyHighlighting);
        // call when no longer need it: `cleanupWhenFinished.unsubscribe();`
        
        codeArea.replaceText(0, 0, this.archivoPython.getContenido());
    }

    private Task<StyleSpans<Collection<String>>> computeHighlightingAsync() {
        String text = this.codeArea.getText();
        Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
            @Override
            protected StyleSpans<Collection<String>> call() throws Exception {
                return ManageTextEditor.computeHighlighting(text);
            }
        };
        this.executor.execute(task);
        return task;
    }

    private void applyHighlighting(StyleSpans<Collection<String>> highlighting) {
        this.codeArea.setStyleSpans(0, highlighting);
    }

    public CodeArea getCodeArea() {
        return this.codeArea;
    }

    public Tab getTab() {
        return tab;
    }

    public void setArchivoPython(ArchivoPython archivoPython) {
        this.archivoPython = archivoPython;
    }
    
    public void stop(){
        this.subscription.unsubscribe();
    }
}
