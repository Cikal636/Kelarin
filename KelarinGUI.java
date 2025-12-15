import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KelarinGUI extends JFrame {
    private ManajerTugas manajer = new ManajerTugas();
    private DefaultTableModel model;

    private JTextField judulField, deskripsiField, deadlineField;
    private JComboBox<Prioritas> prioritasBox;
    private JTable table;

    public KelarinGUI() {
        setTitle("KELARIN. - To Do List");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color bgColor = new Color(14, 59, 46);
        Color accentColor = new Color(215, 163, 34);
        Color textColor = Color.WHITE;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);

        // ===== Judul =====
        JLabel titleLabel = new JLabel("KELARIN.");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(textColor);
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(bgColor);
        titlePanel.add(titleLabel);

        // ===== Form Input =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(bgColor);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);
        gc.anchor = GridBagConstraints.WEST;
        gc.fill = GridBagConstraints.HORIZONTAL;

        judulField = new JTextField(20);
        deskripsiField = new JTextField(20);
        prioritasBox = new JComboBox<>(Prioritas.values());
        deadlineField = new JTextField(20);

        JLabel judulLabel = new JLabel("Mata Kuliah:");
        JLabel deskripsiLabel = new JLabel("Deskripsi:");
        JLabel prioritasLabel = new JLabel("Prioritas:");
        JLabel deadlineLabel = new JLabel("Deadline (dd-MM-yyyy):");

        for (JLabel label : new JLabel[]{judulLabel, deskripsiLabel, prioritasLabel, deadlineLabel}) {
            label.setFont(labelFont);
            label.setForeground(textColor);
        }

        gc.gridx = 0; gc.gridy = 0; formPanel.add(judulLabel, gc);
        gc.gridx = 1; formPanel.add(judulField, gc);
        gc.gridx = 0; gc.gridy = 1; formPanel.add(deskripsiLabel, gc);
        gc.gridx = 1; formPanel.add(deskripsiField, gc);
        gc.gridx = 0; gc.gridy = 2; formPanel.add(prioritasLabel, gc);
        gc.gridx = 1; formPanel.add(prioritasBox, gc);
        gc.gridx = 0; gc.gridy = 3; formPanel.add(deadlineLabel, gc);
        gc.gridx = 1; formPanel.add(deadlineField, gc);

        // ===== Tombol Aksi =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        buttonPanel.setBackground(bgColor);

        JButton tambahBtn = styledButton("Tambah", accentColor, textColor);
        JButton editBtn = styledButton("Edit", accentColor, textColor);
        JButton hapusBtn = styledButton("Hapus", accentColor, textColor);
        JButton sortDeadlineBtn = styledButton("Sort Deadline", accentColor, textColor);
        JButton sortPrioritasBtn = styledButton("Sort Prioritas", accentColor, textColor);

        buttonPanel.add(tambahBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(hapusBtn);
        buttonPanel.add(sortDeadlineBtn);
        buttonPanel.add(sortPrioritasBtn);

        // ===== Tabel =====
        model = new DefaultTableModel(new Object[]{"Judul", "Deskripsi", "Prioritas", "Deadline", "Selesai"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 4 ? Boolean.class : String.class;
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        table = new JTable(model);
        table.setRowHeight(26);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(accentColor);
        table.getTableHeader().setForeground(textColor);

        JScrollPane scrollPane = new JScrollPane(table);

        // ===== Layout Utama =====
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);

        setLayout(new BorderLayout(10, 10));
        add(titlePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // ===== Action Listeners =====
        tambahBtn.addActionListener(e -> tambahTugas());
        editBtn.addActionListener(e -> editTugas());
        hapusBtn.addActionListener(e -> hapusTugas());
        sortDeadlineBtn.addActionListener(e -> sortDeadline());
        sortPrioritasBtn.addActionListener(e -> sortPrioritas());

        model.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (column == 4) {
                Boolean selesai = (Boolean) model.getValueAt(row, column);
                manajer.setSelesai(row, selesai);
            }
        });
    }

    private JButton styledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        return btn;
    }

    private void tambahTugas() {
        String judul = judulField.getText();
        String deskripsi = deskripsiField.getText();
        Prioritas prioritas = (Prioritas) prioritasBox.getSelectedItem();
        String deadline = deadlineField.getText();

        if (judul.isEmpty() || deadline.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Judul dan deadline wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Tugas tugas = new Tugas(judul, deskripsi, prioritas, deadline);
            manajer.tambahTugas(tugas);
            model.addRow(new Object[]{judul, deskripsi, prioritas, deadline, false});
        } catch (DeadlineInvalidException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editTugas() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String judul = judulField.getText();
            String deskripsi = deskripsiField.getText();
            Prioritas prioritas = (Prioritas) prioritasBox.getSelectedItem();
            String deadline = deadlineField.getText();

            try {
                Tugas tugasBaru = new Tugas(judul, deskripsi, prioritas, deadline);
                manajer.editTugas(row, tugasBaru);
                model.setValueAt(judul, row, 0);
                model.setValueAt(deskripsi, row, 1);
                model.setValueAt(prioritas, row, 2);
                model.setValueAt(deadline, row, 3);
            } catch (DeadlineInvalidException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void hapusTugas() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            manajer.hapusTugas(row);
            model.removeRow(row);
        }
    }

    private void sortDeadline() {
        manajer.sortByDeadline();
        refreshTable();
    }

    private void sortPrioritas() {
        manajer.sortByPrioritas();
        refreshTable();
    }

    private void refreshTable() {
        model.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (Tugas t : manajer.getDaftarTugas()) {
            model.addRow(new Object[]{
                    t.getJudul(),
                    t.getDeskripsi(),
                    t.getPrioritas(),
                    t.getDeadline().format(formatter),
                    t.isSelesai()
            });
        }
    }

       public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new KelarinGUI().setVisible(true);
        });
    }
} 
