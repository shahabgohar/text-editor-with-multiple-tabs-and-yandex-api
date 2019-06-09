import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.undo.UndoManager;
import java.util.Date;
public class Model_Notepad {
    private UndoManager undoManager=new UndoManager();
    public JTextArea notepad=new JTextArea();
    public JTextPane textPane=new JTextPane();
    public JScrollPane scrollPane;
    String init_doc;
    int prev_length=0;
    int new_length=0;
    SimpleAttributeSet set;
    String file;
    Document document;
    public void undo()
    {
        if(undoManager.canUndo())
        {
            undoManager.undo();
        }

    }
    public void  redo()
    {
        if(undoManager.canRedo())
        {
            undoManager.redo();
        }
    }
    public JScrollPane new_tab()
    {

         this.set=new SimpleAttributeSet();
        StyleConstants.setFontFamily(set,"times new roman");
        StyleConstants.setFontSize(set,16);
        this.textPane.setCharacterAttributes(set,true);
        this.scrollPane=new JScrollPane(this.textPane);
        this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.init_doc="";
        this.document=this.textPane.getStyledDocument();
        this.document.addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });
        return scrollPane;
    }
    public JScrollPane current_tab()
    {
        return this.scrollPane;
    }

    public void setDocument(String docs)
    {
        this.init_doc+=docs;
    }

    public boolean is_edited(String docs)
    {

            if(this.init_doc.equals(docs))
            {
                return false;
            }else{
                return  true;
            }

    }

    public void add_date()
    {
        Document doc=this.textPane.getStyledDocument();
        Date date=new Date();
        try {
            doc.insertString(this.textPane.getCaretPosition(),date.toString(),this.set);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

    }
    public void copy_text()
    {
       this.textPane.copy();
    }
    public void paste_text()
    {
        this.textPane.paste();
    }
    public void cut_text()
    {
        this.textPane.cut();
    }
    public void replace(String content)
    {
        this.textPane.replaceSelection(content);
    }
    public void  setfilepath(String path)
    {
            this.file=path;
    }
}
