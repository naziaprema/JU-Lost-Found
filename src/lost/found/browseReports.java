package lost.found;

import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/** All visible controls and layout are editable in browseReports.form. */
public class browseReports extends javax.swing.JFrame {
    private final ReportStore store;
    private List<ReportStore.Report> loadedReports = List.of();
    private TableRowSorter<DefaultTableModel> sorter;
    private int skippedFiles;
    private ReportStore.Report selectedReport;
    private SwingWorker<ImageIcon, Void> photoWorker;

    public browseReports() {
        this(new ReportStore());
    }

    browseReports(ReportStore store) {
        this.store = store;
        initComponents();
        reportsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        reportsTable.getTableHeader().setReorderingAllowed(false);
        sorter = new TableRowSorter<>((DefaultTableModel) reportsTable.getModel());
        reportsTable.setRowSorter(sorter);
        int[] widths = {110, 45, 90, 140, 85};
        for (int i = 0; i < widths.length; i++) {
            reportsTable.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }
        reportsTable.getSelectionModel().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting()) showSelectedReport();
        });
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent evt) { applyFilters(); }
            public void removeUpdate(javax.swing.event.DocumentEvent evt) { applyFilters(); }
            public void changedUpdate(javax.swing.event.DocumentEvent evt) { applyFilters(); }
        });
        loadReports();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CampusFind - Browse Reports");
        setResizable(false);
        leftPanel = new javax.swing.JPanel();
        leftPanel.setPreferredSize(new java.awt.Dimension(230, 620));
        leftPanel.setBackground(new java.awt.Color(228, 228, 228));

        brandLabel = new javax.swing.JLabel();
        brandLabel.setText("CampusFind");
        brandLabel.setFont(new java.awt.Font("Segoe UI", 1, 24));
        brandLabel.setHorizontalAlignment(0);

        navDashboardButton = new javax.swing.JButton();
        navDashboardButton.setText("Dashboard");
        navDashboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                navDashboardButtonActionPerformed(evt);
            }
        });

        navBrowseButton = new javax.swing.JButton();
        navBrowseButton.setText("Browse Reports");
        navBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                navBrowseButtonActionPerformed(evt);
            }
        });

        navMatchingButton = new javax.swing.JButton();
        navMatchingButton.setText("Matching Reports");
        navMatchingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                navMatchingButtonActionPerformed(evt);
            }
        });

        navAccountButton = new javax.swing.JButton();
        navAccountButton.setText("Account Data");
        navAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                navAccountButtonActionPerformed(evt);
            }
        });

        contentPanel = new javax.swing.JPanel();
        contentPanel.setPreferredSize(new java.awt.Dimension(530, 620));

        headingLabel = new javax.swing.JLabel();
        headingLabel.setText("Browse Reports");
        headingLabel.setFont(new java.awt.Font("Segoe UI", 1, 26));

        searchLabel = new javax.swing.JLabel();
        searchLabel.setText("Search");

        searchField = new javax.swing.JTextField();
        searchField.setToolTipText("Search item names, categories, descriptions or locations");

        typeFilterComboBox = new javax.swing.JComboBox<>();
        typeFilterComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"All", "Lost", "Found"}));
        typeFilterComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeFilterComboBoxActionPerformed(evt);
            }
        });

        reportsScrollPane = new javax.swing.JScrollPane();

        reportsTable = new javax.swing.JTable();
        reportsTable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
                new String[] {"Item", "Type", "Category", "Location", "Date"}) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        });
        reportsTable.setRowHeight(26);
        reportsTable.setAutoCreateRowSorter(true);

        countLabel = new javax.swing.JLabel();
        countLabel.setText("No saved reports yet.");

        refreshButton = new javax.swing.JButton();
        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        detailsLabel = new javax.swing.JLabel();
        detailsLabel.setText("Selected report");
        detailsLabel.setFont(new java.awt.Font("Segoe UI", 1, 16));

        detailsScrollPane = new javax.swing.JScrollPane();

        detailsArea = new javax.swing.JTextArea();
        detailsArea.setText("Select a report to view its details.");
        detailsArea.setEditable(false);
        detailsArea.setColumns(20);
        detailsArea.setRows(5);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);

        photoLabel = new javax.swing.JLabel();
        photoLabel.setText("No photo");
        photoLabel.setHorizontalAlignment(0);
        photoLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 187, 187)));

        backButton = new javax.swing.JButton();
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup().addGap(15, 15, 15).addComponent(brandLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(15, 15, Short.MAX_VALUE))
            .addGroup(leftPanelLayout.createSequentialGroup().addGap(15, 15, 15).addComponent(navDashboardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(15, 15, Short.MAX_VALUE))
            .addGroup(leftPanelLayout.createSequentialGroup().addGap(15, 15, 15).addComponent(navBrowseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(15, 15, Short.MAX_VALUE))
            .addGroup(leftPanelLayout.createSequentialGroup().addGap(15, 15, 15).addComponent(navMatchingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(15, 15, Short.MAX_VALUE))
            .addGroup(leftPanelLayout.createSequentialGroup().addGap(15, 15, 15).addComponent(navAccountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(15, 15, Short.MAX_VALUE))
        );
        leftPanelLayout.setVerticalGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup().addGap(60, 60, 60).addComponent(brandLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(520, 520, Short.MAX_VALUE))
            .addGroup(leftPanelLayout.createSequentialGroup().addGap(180, 180, 180).addComponent(navDashboardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(395, 395, Short.MAX_VALUE))
            .addGroup(leftPanelLayout.createSequentialGroup().addGap(240, 240, 240).addComponent(navBrowseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(335, 335, Short.MAX_VALUE))
            .addGroup(leftPanelLayout.createSequentialGroup().addGap(300, 300, 300).addComponent(navMatchingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(275, 275, Short.MAX_VALUE))
            .addGroup(leftPanelLayout.createSequentialGroup().addGap(360, 360, 360).addComponent(navAccountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(215, 215, Short.MAX_VALUE))
        );

        reportsScrollPane.setViewportView(reportsTable);

        detailsScrollPane.setViewportView(detailsArea);

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(30, 30, 30).addComponent(headingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(30, 30, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(30, 30, 30).addComponent(searchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(445, 445, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(90, 90, 90).addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(195, 195, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(350, 350, 350).addComponent(typeFilterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(30, 30, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(30, 30, 30).addComponent(reportsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(30, 30, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(30, 30, 30).addComponent(countLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(155, 155, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(390, 390, 390).addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(30, 30, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(30, 30, 30).addComponent(detailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(30, 30, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(30, 30, 30).addComponent(detailsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(190, 190, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(360, 360, 360).addComponent(photoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(30, 30, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(30, 30, 30).addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(390, 390, Short.MAX_VALUE))
        );
        contentPanelLayout.setVerticalGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(22, 22, 22).addComponent(headingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(560, 560, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(80, 80, 80).addComponent(searchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(518, 518, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(75, 75, 75).addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(515, 515, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(75, 75, 75).addComponent(typeFilterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(515, 515, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(125, 125, 125).addComponent(reportsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(285, 285, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(346, 346, 346).addComponent(countLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(250, 250, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(342, 342, 342).addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(248, 248, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(384, 384, 384).addComponent(detailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(209, 209, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(421, 421, 421).addComponent(detailsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(74, 74, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(421, 421, 421).addComponent(photoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(74, 74, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(568, 568, 568).addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(20, 20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addGap(0, 0, 0).addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(530, 530, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup().addGap(230, 230, 230).addComponent(contentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addGap(0, 0, 0).addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup().addGap(0, 0, 0).addComponent(contentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void loadReports() {
        if (!refreshButton.isEnabled()) return;
        refreshButton.setEnabled(false);
        countLabel.setText("Loading reports...");
        new SwingWorker<ReportStore.LoadResult, Void>() {
            @Override protected ReportStore.LoadResult doInBackground() throws Exception {
                return store.loadAll();
            }

            @Override protected void done() {
                if (!isDisplayable()) return;
                refreshButton.setEnabled(true);
                try {
                    ReportStore.LoadResult result = get();
                    loadedReports = result.reports();
                    skippedFiles = result.skippedFiles();
                    DefaultTableModel model = (DefaultTableModel) reportsTable.getModel();
                    model.setRowCount(0);
                    for (ReportStore.Report report : loadedReports) {
                        model.addRow(new Object[] {report.itemName(), report.type(), report.category(),
                            report.location(), report.date().toString()});
                    }
                    applyFilters();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    showLoadError();
                } catch (ExecutionException ex) {
                    showLoadError();
                }
            }
        }.execute();
    }

    private void showLoadError() {
        countLabel.setText("Could not refresh reports.");
        JOptionPane.showMessageDialog(this,
                "Could not read the saved reports. Check that the data folder is accessible.",
                "Could not load reports", JOptionPane.ERROR_MESSAGE);
    }

    private void applyFilters() {
        if (sorter == null) return;
        final String query = searchField.getText();
        final String type = (String) typeFilterComboBox.getSelectedItem();
        sorter.setRowFilter(new javax.swing.RowFilter<DefaultTableModel, Integer>() {
            @Override public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                return loadedReports.get(entry.getIdentifier()).matches(query, type);
            }
        });
        if (reportsTable.getRowCount() > 0) {
            reportsTable.setRowSelectionInterval(0, 0);
        } else {
            reportsTable.clearSelection();
        }
        showSelectedReport();
        String count = "Showing " + reportsTable.getRowCount() + " of " + loadedReports.size() + " reports";
        if (loadedReports.isEmpty()) count = "No saved reports yet.";
        else if (reportsTable.getRowCount() == 0) count = "No matching reports.";
        if (skippedFiles > 0) count += " (" + skippedFiles + " unreadable)";
        countLabel.setText(count);
        countLabel.setToolTipText(count);
    }

    private void showSelectedReport() {
        if (photoWorker != null) photoWorker.cancel(true);
        photoLabel.setIcon(null);
        photoLabel.setText("No photo");
        selectedReport = null;
        int selected = reportsTable.getSelectedRow();
        if (selected < 0) {
            detailsArea.setText("Select a report to view its details.");
            return;
        }
        int row = reportsTable.convertRowIndexToModel(selected);
        if (row >= loadedReports.size()) return;
        ReportStore.Report report = loadedReports.get(row);
        selectedReport = report;
        detailsArea.setText("Item: " + report.itemName()
                + "\nType: " + report.type()
                + "\nCategory: " + report.category()
                + "\nLocation: " + report.location()
                + "\nDate: " + report.date()
                + "\nStatus: " + report.status()
                + "\n\nDescription:\n" + report.description());
        detailsArea.setCaretPosition(0);
        Path photo = store.photoPath(report);
        if (photo == null) {
            if (!report.photo().isBlank()) photoLabel.setText("Photo unavailable");
            return;
        }
        photoLabel.setText("Loading photo...");
        final int width = Math.max(1, photoLabel.getWidth() - 8);
        final int height = Math.max(1, photoLabel.getHeight() - 8);
        photoWorker = new SwingWorker<ImageIcon, Void>() {
            @Override protected ImageIcon doInBackground() throws Exception {
                if (!Files.isRegularFile(photo)) return null;
                BufferedImage source = javax.imageio.ImageIO.read(photo.toFile());
                if (source == null) return null;
                double scale = Math.min((double) width / source.getWidth(),
                        (double) height / source.getHeight());
                int imageWidth = Math.max(1, (int) (source.getWidth() * scale));
                int imageHeight = Math.max(1, (int) (source.getHeight() * scale));
                BufferedImage preview = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
                java.awt.Graphics2D graphics = preview.createGraphics();
                graphics.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                        java.awt.RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                graphics.drawImage(source, 0, 0, imageWidth, imageHeight, null);
                graphics.dispose();
                return new ImageIcon(preview);
            }

            @Override protected void done() {
                if (isCancelled() || !isDisplayable() || selectedReport != report) return;
                try {
                    ImageIcon icon = get();
                    photoLabel.setIcon(icon);
                    photoLabel.setText(icon == null ? "Photo unavailable" : "");
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    photoLabel.setText("Photo unavailable");
                } catch (ExecutionException ex) {
                    photoLabel.setText("Photo unavailable");
                }
            }
        };
        photoWorker.execute();
    }

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        loadReports();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void typeFilterComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeFilterComboBoxActionPerformed
        applyFilters();
    }//GEN-LAST:event_typeFilterComboBoxActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        returnToDashboard();
    }//GEN-LAST:event_backButtonActionPerformed

    private void navDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navDashboardButtonActionPerformed
        returnToDashboard();
    }//GEN-LAST:event_navDashboardButtonActionPerformed

    private void navBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navBrowseButtonActionPerformed
        loadReports();
    }//GEN-LAST:event_navBrowseButtonActionPerformed

    private void navMatchingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navMatchingButtonActionPerformed
        JOptionPane.showMessageDialog(this, "Matching Reports will be added next.");
    }//GEN-LAST:event_navMatchingButtonActionPerformed

    private void navAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navAccountButtonActionPerformed
        JOptionPane.showMessageDialog(this, "Account data screen will be added later.");
    }//GEN-LAST:event_navAccountButtonActionPerformed

    private void returnToDashboard() {
        new dashboard().setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new browseReports().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel leftPanel;
    private javax.swing.JLabel brandLabel;
    private javax.swing.JButton navDashboardButton;
    private javax.swing.JButton navBrowseButton;
    private javax.swing.JButton navMatchingButton;
    private javax.swing.JButton navAccountButton;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JLabel headingLabel;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JTextField searchField;
    private javax.swing.JComboBox<String> typeFilterComboBox;
    private javax.swing.JScrollPane reportsScrollPane;
    private javax.swing.JTable reportsTable;
    private javax.swing.JLabel countLabel;
    private javax.swing.JButton refreshButton;
    private javax.swing.JLabel detailsLabel;
    private javax.swing.JScrollPane detailsScrollPane;
    private javax.swing.JTextArea detailsArea;
    private javax.swing.JLabel photoLabel;
    private javax.swing.JButton backButton;
    // End of variables declaration//GEN-END:variables
}
