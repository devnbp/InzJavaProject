package ua.edu.sumdu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class BookCreateView extends View {
    private JLabel nameLabel = new JLabel("Name:");
    private JTextField name = new JTextField(16);
    private JLabel authorLabel = new JLabel("Author:");
    private JTextField author = new JTextField(16);
    private JLabel publisherLabel = new JLabel("Publisher:");
    private JTextField publisher = new JTextField(16);
    private JLabel yearLabel = new JLabel("Year:");
    private JTextField year = new JTextField(16);
    private JLabel contentLabel = new JLabel("Text:");
    private JTextArea  content = new JTextArea();
    private JButton createButton = new JButton("Add");

    public BookCreateView(List<Book> books, DefaultListModel<String> list) {
        content.setLineWrap(true);
        content.setWrapStyleWord(true);
        content.setBorder(BorderFactory.createLineBorder(Color.black));
        content.setMinimumSize(new Dimension(300, 300));
        this.setBorder(BorderFactory.createTitledBorder("Add a new book:"));
        this.setBackground(Color.WHITE);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(nameLabel)
                        .addComponent(authorLabel)
                        .addComponent(publisherLabel)
                        .addComponent(yearLabel)
                        .addComponent(contentLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(name)
                        .addComponent(author)
                        .addComponent(publisher)
                        .addComponent(year)
                        .addComponent(content)
                        .addComponent(createButton))
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(nameLabel)
                        .addComponent(name))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(authorLabel)
                        .addComponent(author))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(publisherLabel)
                        .addComponent(publisher))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(yearLabel)
                        .addComponent(year))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(contentLabel)
                        .addComponent(content))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(createButton))
        );
        createButton.addActionListener( new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Book newBook = BookController.store(name.getText(), author.getText(), publisher.getText(), Integer.parseInt(year.getText()), content.getText());
                books.add(newBook);
                list.addElement(newBook.getFieldValue("name").toString());
                SwingUtilities.getWindowAncestor(createButton).dispose();
            }
        });
    }
}
