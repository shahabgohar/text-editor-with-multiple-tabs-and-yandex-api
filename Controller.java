import co.aurasphere.jyandex.Jyandex;
import co.aurasphere.jyandex.dto.Language;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class Controller {
    ArrayList<first_location> occurences=new ArrayList<first_location>();
    String temp_text="";
    private int isSetFirstLocation=0;
    int counter=0;
    int count_prev=0;
    String search_text;
    private JFrame main_frame;
    private JMenuBar menubar;
    private Highlighter.HighlightPainter cyanPainter;
    private JMenu file=new JMenu("File");
    private JMenu edit=new JMenu("Edit");
    private JMenu Format=new JMenu("Format");
    private JMenu help=new JMenu("Help");
    private  JButton meaning=new JButton("get Meaning for you");
    private JMenuItem NEW=new JMenuItem("New");
    private JMenuItem open=new JMenuItem("Open");
    private JMenuItem save=new JMenuItem("Save");
    private JMenuItem save_as = new JMenuItem("Save as");
    private JMenuItem close_current = new JMenuItem("Close Current Tab");
    private JMenuItem openNewTab = new JMenuItem("Open New Tab");
    Font font1 = new Font("SansSerif", Font.BOLD, 30);
    private JMenuItem exit=new JMenuItem("Exit");
    private JMenuItem undo=new JMenuItem("Undo");
    private JMenuItem cut=new JMenuItem("Cut");
    private JMenuItem copy=new JMenuItem("Copy");
    private JMenuItem paste = new JMenuItem("Paste");
    private JMenuItem Find=new JMenuItem("Find");
    private JMenuItem Select_all = new JMenuItem("Select all");
    private JMenuItem Timeordate=new JMenuItem("Time/Date");
    private JMenuItem text_style=new JMenuItem("Font");
    private int mouse_clicked=0;
    JFileChooser open_as_dialodue=new JFileChooser();
    private JTabbedPane tabbedPane=new JTabbedPane();
    private JButton close_search=new JButton("Search");
    private  JTextField find_meaning=new JTextField();
    private JButton close_meaning=new JButton("close");
    Jyandex client = new Jyandex();
    private int output;
    private JTextField search =new JTextField();
    private StringBuffer SearchText=new StringBuffer();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
    ArrayList<Model_Notepad> tabs=new ArrayList<Model_Notepad>();
    private JMenuItem redo =new JMenuItem("Redo");
    private JMenuItem replace =new JMenuItem("Replace");
    public Controller() {
        this.initialize();
    }

    private JFrame intitialize_frame(JFrame frame)
    {
        frame=new JFrame("main frame");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        return frame;
    }
    private JMenuBar init_menubar(JMenuBar menuBar)
    {
        menuBar=new JMenuBar();
        return menuBar;
    }
    private void add_menu_items_menubar(JMenuItem item)
    {
        this.menubar.add(item);
    }
    private void set_menu(JFrame frame,JMenuBar menuBar)
    {
        frame.setJMenuBar(menuBar);
    }
    private void add_menu_options(JMenu menu,JMenuItem item)
    {
        menu.add(item);
        menu.addSeparator();
    }
    private void initialize()
    {
        this.main_frame=this.intitialize_frame(this.main_frame);
        this.menubar=this.init_menubar(this.menubar);
        this.add_menu_items_menubar(this.file);
        this.add_menu_items_menubar(this.edit);
        this.add_menu_items_menubar(this.Format);
        this.add_menu_items_menubar(this.help);
        this.set_menu(this.main_frame,this.menubar);
        this.menubar.add(search);
        this.menubar.add(close_search);
        this.menubar.add(this.find_meaning);
        this.menubar.add(this.close_meaning);
        this.menubar.add(this.meaning);
        search.setVisible(false);
        close_search.setVisible(false);
        this.add_menu_options(this.file,this.NEW);
        this.add_menu_options(this.file,this.open);
        this.add_menu_options(this.file,this.save);
        this.add_menu_options(this.file,this.save_as);
        this.add_menu_options(this.file,this.close_current);
        this.add_menu_options(this.file,this.openNewTab);
        this.add_menu_options(this.file,this.exit);
        this.add_menu_options(this.edit,this.undo);
        this.add_menu_options(this.edit,this.redo);
        this.add_menu_options(this.edit,this.cut);
        this.add_menu_options(this.edit,this.copy);
        this.add_menu_options(this.edit,this.paste);
        this.add_menu_options(this.edit,this.Find);
        this.add_menu_options(this.edit,this.Select_all);
        this.add_menu_options(this.edit,this.Timeordate);
        this.add_menu_options(this.edit,this.Find);
        this.add_menu_options(this.Format,this.text_style);
        this.add_menu_options(this.edit,this.replace);
        this.find_meaning.setFont(font1);
        this.find_meaning.setVisible(false);
        this.close_meaning.setVisible(false);
        this.meaning.setVisible(true);
        tabs.add(new Model_Notepad());
        tabbedPane.add("untittled",tabs.get(0).new_tab());
        main_frame.setLocationByPlatform(true);
        main_frame.add(tabbedPane);
        main_frame.pack();
        main_frame.setVisible(true);
        cyanPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.cyan);
        main_frame.setSize(600,600);

        this.open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if the tab opened is the first one and contains no content then open a file and paste the content in it
                open_as_dialodue.setFileFilter(filter);
                output=open_as_dialodue.showOpenDialog(main_frame);
                if(output==JFileChooser.APPROVE_OPTION)
                {
                    File file1=open_as_dialodue.getSelectedFile();
                    System.out.println(open_as_dialodue.getSelectedFile().getName());
                    tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), open_as_dialodue.getSelectedFile().getName());

                    StringBuffer st=new StringBuffer();
                    Scanner DatatoTextpane;
                    try {
                        DatatoTextpane=new Scanner(file1);
                        while (DatatoTextpane.hasNextLine())
                        {

                            st.append(DatatoTextpane.nextLine());

                        }
                        tabs.get(tabbedPane.getSelectedIndex()).setfilepath(file1.getAbsolutePath());
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    System.out.println("the selected file is : "+st.toString());
                    tabs.get(tabbedPane.getSelectedIndex()).textPane.setText(st.toString());
                    tabs.get(tabbedPane.getSelectedIndex()).setDocument(st.toString());
                }

            }
        });


        KeyStroke stroke=KeyStroke.getKeyStroke("control O");
        this.open.setAccelerator(stroke);
        KeyStroke newtabstroke=KeyStroke.getKeyStroke("control T");
        this.openNewTab.setAccelerator(newtabstroke);
        KeyStroke closetabstroke=KeyStroke.getKeyStroke("control W");
        this.close_current.setAccelerator(closetabstroke);
        final KeyStroke findkeys=KeyStroke.getKeyStroke("control F");
        this.Find.setAccelerator(findkeys);
        KeyStroke save=KeyStroke.getKeyStroke("control S");
        this.save.setAccelerator(save);
//        KeyStroke saveas=KeyStroke.getKeyStroke("control shift s");
//        this.save_as.setAccelerator(saveas);

        this.openNewTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabs.add(new Model_Notepad());
                tabbedPane.add("untiitled",tabs.get(tabs.size()-1).new_tab());
                tabbedPane.setSelectedIndex(tabs.size()-1);
            }
        });
        this.search.addKeyListener(new KeyAdapter() {
                                       @Override
                                       public void keyReleased(KeyEvent e) {
                                           System.out.println(e.getKeyCode());
                                           if (e.getKeyCode()!=10) {
                                               int start = -1;
                                               if (search.getText().length() == 1) {
                                                   // tabs.get(tabbedPane.getSelectedIndex()).textPane.getHighlighter().removeAllHighlights();
                                                   if (!occurences.isEmpty()) {
                                                       occurences.clear();
                                                   }
                                                   search_text = search.getText();
                                                   temp_text = tabs.get(tabbedPane.getSelectedIndex()).textPane.getText();
                                                   start = tabs.get(tabbedPane.getSelectedIndex()).textPane.getText().indexOf( search.getText());
                                                   while (start >= 0) {
                                                       occurences.add(new first_location().setNumber_start(start));
                                                       System.out.println("value of start is : " + start);

                                                       start = tabs.get(tabbedPane.getSelectedIndex()).textPane.getText().indexOf( search.getText(), start + search_text.length());

                                                   }
                                                   counter++;
                                               }
                                               //setting the endstring of seaarching string
                                               for (int i = 0; i < occurences.size(); i++) {
                                                   if (occurences.get(i).number_start + search.getText().length() > tabs.get(tabbedPane.getSelectedIndex()).textPane.getText().length()) {
                                                       occurences.remove(i);
                                                   } else {
                                                       occurences.get(i).setNumber_final(search.getText().length() + occurences.get(i).number_start);
                                                   }

                                               }
                                               tabs.get(tabbedPane.getSelectedIndex()).textPane.getHighlighter().removeAllHighlights();
                                               //now highlighting
                                               int counts = 0;
                                               for (int i = 0; i < occurences.size(); i++) {
                                                   if (search.getText().equals(tabs.get(tabbedPane.getSelectedIndex()).textPane.getText().substring(occurences.get(i).number_start, occurences.get(i).number_final))) {
                                                       try {
                                                           tabs.get(tabbedPane.getSelectedIndex()).textPane.getHighlighter().addHighlight(occurences.get(i).number_start, occurences.get(i).number_final, cyanPainter);
                                                           occurences.get(i).setIs_matched(true);
                                                       } catch (BadLocationException ex) {
                                                           ex.printStackTrace();
                                                       }
                                                   } else {
                                                       occurences.get(i).setIs_matched(false);
                                                   }
                                               }
                                               for (int i = 0; i < occurences.size(); i++) {
                                                   if (occurences.get(i).is_matched == true) {
                                                       tabs.get(tabbedPane.getSelectedIndex()).textPane.setCaretPosition(occurences.get(i).number_start);
                                                       break;
                                                   }
                                               }
                                           }else{
//                                               tabs.get(tabbedPane.getSelectedIndex()).textPane.setEditable(false);
                                               tabs.get(tabbedPane.getSelectedIndex()).textPane.requestFocusInWindow();
                                           }
                                       }
                                   }
        );
        this.NEW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                class new_notepad extends Thread{
                    @Override
                    public void run() {
                        new Controller();
                    }
                }
                Thread t1=new new_notepad();
                t1.start();
            }
        });
        KeyStroke undo_key=KeyStroke.getKeyStroke("control Z");
        this.undo.setAccelerator(undo_key);
        KeyStroke redo_key=KeyStroke.getKeyStroke("control Y");
        this.redo.setAccelerator(redo_key);
        KeyStroke cut_key=KeyStroke.getKeyStroke("control X");
        this.cut.setAccelerator(cut_key);
        KeyStroke paste_key=KeyStroke.getKeyStroke("control V");
        this.paste.setAccelerator(paste_key);
        KeyStroke all_key=KeyStroke.getKeyStroke("control A");
        this.Select_all.setAccelerator(all_key);
        KeyStroke copy_key=KeyStroke.getKeyStroke("control C");
        this.copy.setAccelerator(copy_key);

        this.Find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                meaning.setVisible(false);
                close_search.setVisible(true);
                meaning.setVisible(true);

                search.setVisible(true);
            }
        });
        this.close_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search.setText("");
                search.setVisible(false);
                if(!occurences.isEmpty())
                {
                    occurences.clear();
                }
                close_search.setVisible(false);
            }
        });
        this.close_meaning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                find_meaning.setText("");
                find_meaning.setVisible(false);
                close_meaning.setVisible(false);
                meaning.setVisible(true);
            }
        });
        KeyStroke keyformeaning=KeyStroke.getKeyStroke("Control M");


        this.meaning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                class finding_meaning extends Thread{
                    @Override
                    public void run() {
                        if(tabs.get(tabbedPane.getSelectedIndex()).textPane.getSelectedText()!=null) {
                            find_meaning.setVisible(true);
                            close_meaning.setVisible(true);
                            meaning.setVisible(false);
                            find_meaning.setEditable(false);
                            find_meaning.setText(getmeaning(tabs,tabbedPane,client));
                        }
                    }
                }
                Thread t1=new finding_meaning();
                t1.start();
                try {
                    t1.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.close_current.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tabs.get(tabbedPane.getSelectedIndex()).textPane.getText().length()==0)
                {
                    tabbedPane.remove(tabbedPane.getSelectedIndex());
                    if(tabbedPane.getTabCount()==0)
                    {
                        System.exit(0);
                    }
                }else{
                    if(tabs.get(tabbedPane.getSelectedIndex()).is_edited(tabs.get(tabbedPane.getSelectedIndex()).textPane.getText()))
                    {
                        open_as_dialodue.setFileFilter(filter);
                        int rc=open_as_dialodue.showSaveDialog(main_frame);
                        if(rc==JFileChooser.APPROVE_OPTION)
                        {
                            File file=open_as_dialodue.getSelectedFile();
                            String filename=open_as_dialodue.getSelectedFile().getName();
                            try {
                                FileWriter fileWriter=new FileWriter(file.getAbsolutePath());
                                fileWriter.write(tabs.get(tabbedPane.getSelectedIndex()).textPane.getText());
                                fileWriter.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            tabbedPane.remove(tabbedPane.getSelectedIndex());
                        }

                    }else{
                        tabbedPane.remove(tabbedPane.getSelectedIndex());
                        if(tabbedPane.getTabCount()==0)
                        {
                            System.exit(0);
                        }
                    }
                }
            }
        });
        this.Timeordate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabs.get(tabbedPane.getSelectedIndex()).add_date();
            }
        });
        KeyStroke time_key=KeyStroke.getKeyStroke("control D");
        this.Timeordate.setAccelerator(time_key);
        this.tabs.get(tabbedPane.getSelectedIndex()).textPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==112 && search.isVisible()==true)
                {
//                    tabs.get(tabbedPane.getSelectedIndex()).textPane.setEditable(false);
                    for(int i=0;i<occurences.size();i++)
                    {
                        if(occurences.get(i).is_visited==false && occurences.get(i).is_matched==true)
                        {
                            tabs.get(tabbedPane.getSelectedIndex()).textPane.setCaretPosition(occurences.get(i).number_start);
                            occurences.get(i).setIs_visited(true);
                            break;
                        }
                    }
//                    tabs.get(tabbedPane.getSelectedIndex()).textPane.setEditable(true);
                }
            }
        });
        this.cut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabs.get(tabbedPane.getSelectedIndex()).cut_text();
            }
        });
        this.copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabs.get(tabbedPane.getSelectedIndex()).copy_text();
            }
        });
        this.paste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabs.get(tabbedPane.getSelectedIndex()).paste_text();
            }
        });
        this.replace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tabs.get(tabbedPane.getSelectedIndex()).textPane.getSelectedText()!=null)
                {
                    String SelectedText=JOptionPane.showInputDialog(main_frame,"Enter the syntax you wanna replace : ");
                    tabs.get(tabbedPane.getSelectedIndex()).replace(SelectedText);
                }else{
                    JOptionPane.showMessageDialog(main_frame,"You didnot select any text");
                }

            }
        });
        this.save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if(tabs.get(tabbedPane.getSelectedIndex()).textPane.getText().length()!=0)
                    {
                        FileWriter fw=new FileWriter(tabs.get(tabbedPane.getSelectedIndex()).file);
                        fw.write(tabs.get(tabbedPane.getSelectedIndex()).textPane.getText());
                        fw.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.tabs.get(tabbedPane.getSelectedIndex()).textPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(e.getClickCount()==2)
                {
                    //area is selected
                    class finding_meaning_thread extends Thread{
                        private MouseEvent e;
                        public finding_meaning_thread(MouseEvent e)
                        {
                            this.e=e;
                        }
                        @Override
                        public void run() {
                            JPopupMenu popupMenu=new JPopupMenu();
                            popupMenu.add( getmeaning(tabs,tabbedPane,client));
                            popupMenu.show(e.getComponent(),e.getX(),e.getY());
                        }
                    }
                    Thread t1=new finding_meaning_thread(e);
                    t1.start();

                }

            }
        });
        this.Select_all.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabs.get(tabbedPane.getSelectedIndex()).textPane.selectAll();
            }
        });


        this.undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabs.get(tabbedPane.getSelectedIndex()).undo();
            }
        });

        this.redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabs.get(tabbedPane.getSelectedIndex()).redo();
            }
        });

        this.save_as.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                open_as_dialodue.setFileFilter(filter);
                int rc=open_as_dialodue.showSaveDialog(main_frame);

                if(rc==JFileChooser.APPROVE_OPTION)
                {
                    File file=open_as_dialodue.getSelectedFile();
                    tabs.get(tabbedPane.getSelectedIndex()).setfilepath(file.getAbsolutePath());
                    String filename=open_as_dialodue.getSelectedFile().getName();
                    if(filename!=tabs.get(tabbedPane.getSelectedIndex()).file)
                    {
                        tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(),open_as_dialodue.getSelectedFile().getName());

                    try {
                        FileWriter fileWriter=new FileWriter(file.getAbsolutePath());
                        fileWriter.write(tabs.get(tabbedPane.getSelectedIndex()).textPane.getText());
                        fileWriter.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    }else{
                        JOptionPane.showMessageDialog(main_frame,"Filename already exist");
                    }
                }
            }
        });
this.help.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(main_frame,"Contact : imshahabgophar1230@gmail.com");
    }
});

this.text_style.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        SimpleAttributeSet set=new SimpleAttributeSet();
        StyleConstants.setFontFamily(set,"regular");
        StyleConstants.setFontSize(set,22);
        StyleConstants.setForeground(set,Color.WHITE);
        StyleConstants.setBackground(set,Color.BLACK);
        tabs.get(tabbedPane.getSelectedIndex()).textPane.setCharacterAttributes(set,true);
    }
});

    }
    public String getmeaning(ArrayList<Model_Notepad> tabs,JTabbedPane tabbedPane,Jyandex client)
    {

        System.out.println(tabs.get(tabbedPane.getSelectedIndex()).textPane.getSelectedText());
        StringBuffer stringBuffer = new StringBuffer(client.translateText(tabs.get(tabbedPane.getSelectedIndex()).textPane.getSelectedText(), Language.URDU).toString());
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.replace(0,stringBuffer.length(),stringBuffer.toString().replace("TranslateTextResponse [text=["," "));

        return stringBuffer.toString();
    }
}