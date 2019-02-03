package ua.edu.sumdu;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

public class BookView extends View{
    private List<Book> books = BookController.list();
    private DefaultListModel<String> list = new DefaultListModel<>();
    private JList bookList = new JList(list);
    private JScrollPane scrollList = new JScrollPane(bookList);
    private JButton addButton = new JButton("Add");
    private JButton deleteButton = new JButton("Delete");
    private JLabel infoLabel = new JLabel("");
    private JLabel pageLabel = new JLabel("");
    private int currPage = 0;
    private JButton backButton = new JButton("<<");
    private JButton nextButton = new JButton(">>");

    public BookView() {
        for (Book book : books){
            list.addElement(book.getFieldValue("name").toString());
        }
        bookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookList.setLayoutOrientation(JList.VERTICAL);
        bookList.setVisibleRowCount(5);
        Dimension listSize = new Dimension(200, 150);
        scrollList.setMaximumSize(listSize);
        deleteButton.setEnabled(false);
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createTitledBorder("Book viewer"));
        this.setPreferredSize(new Dimension(600, 800));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(scrollList)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(addButton)
                                        .addComponent(backButton)
                                )
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(deleteButton)
                                        .addComponent(nextButton)
                                )
                        )
                        .addComponent(infoLabel)
                        .addComponent(pageLabel)
                )
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(scrollList))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(addButton)
                        .addComponent(deleteButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(infoLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(pageLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(backButton)
                        .addComponent(nextButton))
        );
        addButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BookController.create(books, list);
            }
        });
        deleteButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Book currBook = books.get(bookList.getSelectedIndex());
                books.remove(bookList.getSelectedIndex());
                list.remove(bookList.getSelectedIndex());
                BookController.delete(currBook);
            }
        });

        bookList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (bookList.getSelectedIndex() >= 0) {
                    deleteButton.setEnabled(true);
                    Book currBook = books.get(bookList.getSelectedIndex());
                    String str = "Name: " + currBook.getFieldValue("name").toString() +
                            ", author: " + currBook.getFieldValue("author").toString() +
                            ", publisher: " + currBook.getFieldValue("publisher") +
                            ", year: " + currBook.getFieldValue("year");
                    infoLabel.setText(str);
                    currPage = 0;
                    pageLabel.setText(currBook.getPage(currPage));
                } else {
                    deleteButton.setEnabled(false);
                    infoLabel.setText("");
                    pageLabel.setText("");
                }
            }
        });

        backButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currPage > 0){
                    Book currBook = books.get(bookList.getSelectedIndex());
                    pageLabel.setText(currBook.getPage(currPage - 1));
                    currPage -= 1;
                }
            }
        });

        nextButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Book currBook = books.get(bookList.getSelectedIndex());
                if (currPage < (Integer) currBook.getFieldValue("numPages") - 1){
                    pageLabel.setText(currBook.getPage(currPage + 1));
                    currPage += 1;
                }
            }
        });
    }
}
