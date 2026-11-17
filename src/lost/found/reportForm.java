package lost.found;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;

/** Lost/found report form. Edit visible controls in reportForm.form using Design. */
public class reportForm extends javax.swing.JFrame {
    private Path selectedPhoto;

    public reportForm() {
        initComponents();
        dateField.setText(LocalDate.now().toString());
        getRootPane().setDefaultButton(saveButton);
    }

    public reportForm(String type) {
        this();
        if (!"Lost".equals(type) && !"Found".equals(type)) {
            throw new IllegalArgumentException("Report type must be Lost or Found.");
        }
        typeComboBox.setSelectedItem(type);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CampusFind - Report an Item");
        setResizable(false);
        leftPanel = new javax.swing.JPanel();
        leftPanel.setBackground(new java.awt.Color(0xe4e4e4));
        leftPanel.setPreferredSize(new java.awt.Dimension(230, 620));

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
        headingLabel.setText("Report an Item");
        headingLabel.setFont(new java.awt.Font("Segoe UI", 1, 26));

        typeLabel = new javax.swing.JLabel();
        typeLabel.setText("Report type");

        typeComboBox = new javax.swing.JComboBox<>();
        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Lost", "Found"}));

        nameLabel = new javax.swing.JLabel();
        nameLabel.setText("Item name *");

        itemNameField = new javax.swing.JTextField();

        categoryLabel = new javax.swing.JLabel();
        categoryLabel.setText("Category *");

        categoryComboBox = new javax.swing.JComboBox<>();
        categoryComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Select a category", "Electronics", "Documents", "Keys", "Clothing", "Accessories", "Personal belongings", "Other"}));

        descriptionLabel = new javax.swing.JLabel();
        descriptionLabel.setText("Description *");

        descriptionScrollPane = new javax.swing.JScrollPane();

        descriptionArea = new javax.swing.JTextArea();
        descriptionArea.setColumns(20);
        descriptionArea.setRows(3);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        locationLabel = new javax.swing.JLabel();
        locationLabel.setText("Location *");

        locationComboBox = new javax.swing.JComboBox<>();
        locationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
            "Select a campus location",
            "Math & Statistics Building",
            "Social Science Faculty",
            "Computer Science And Engneering",
            "Chemistry Department",
            "Physics Department",
            "Shohid Minar",
            "Dairy Gate",
            "Faculty of Arts and Humanities",
            "Department of Microbiology",
            "Department of Biochemistry & Molecular Biology",
            "Department of Accounting & Information Systems",
            "Old Arts Building",
            "Central Cafeteria",
            "Central Mosque",
            "Central Mandir",
            "Wazed Miah Science Research Centre",
            "Gymnasium",
            "Swimmimg Pool",
            "Ju main gate",
            "Agrani Bank PLC",
            "Tarzan Point",
            "Selim Al Deen Open Stage",
            "TSC",
            "Central Library",
            "Zahir Raihan Auditorium",
            "Faculty of Bussiness Studies",
            "Medical Centre",
            "Chourongi",
            "New Register Building",
            "Birprotik taramon bibi hall",
            "Fazilatunnessa hall ju",
            "Rokeya hallA2",
            "Bongomata fazilatunnessa hall",
            "Sufia kamal hall",
            "Pretelata hall",
            "Jahanara Imam hall",
            "Sheikh hasina hall ]",
            "Khaleda Jia hall",
            "Nawab fayjunnessa hall",
            "AL beruni hall",
            "AFM kamaluddin hall",
            "Salam borkat hall",
            "Rafique Jabbar hall",
            "Sheikkh mujib hall",
            "Robi Tagore hall",
            "Mawalana vasani hall",
            "kazi najrul hall",
            "ju school and college",
            "public heath and informatics",
            "Drama and Dramatics",
            "CLC dept",
            "prantik gate mosque",
            "New Central Library",
            "Prantik Gate",
            "Bishmile gate",
            "Mohua Tola",
            "Central Field",
            "JU Bus Depot",
            "Ju Botanical Garden",
            "old register building",
            "Transport",
            "DPHI",
            "Jucsu Building",
            "Shafi Chottor",
            "Munni Chottor",
            "Mahbub Chottor",
            "English Department",
            "Dept Geological Science",
            "Pharmacy",
            "Switzerland",
            "Shantiniketon",
            "Hony moon Island"
        }));
        locationComboBox.setMaximumRowCount(12);

        dateLabel = new javax.swing.JLabel();
        dateLabel.setText("Date *");

        dateField = new javax.swing.JTextField();
        dateField.setToolTipText("Use yyyy-MM-dd, for example 2026-11-17");

        dateHintLabel = new javax.swing.JLabel();
        dateHintLabel.setText("Format: yyyy-MM-dd");
        dateHintLabel.setFont(new java.awt.Font("Segoe UI", 0, 11));

        photoLabel = new javax.swing.JLabel();
        photoLabel.setText("Photo (optional)");

        choosePhotoButton = new javax.swing.JButton();
        choosePhotoButton.setText("Choose Photo");
        choosePhotoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choosePhotoButtonActionPerformed(evt);
            }
        });

        photoNameLabel = new javax.swing.JLabel();
        photoNameLabel.setText("No photo selected");

        saveButton = new javax.swing.JButton();
        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        backButton = new javax.swing.JButton();
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        requiredLabel = new javax.swing.JLabel();
        requiredLabel.setText("* Required fields. Date is when the item was lost or found.");
        requiredLabel.setFont(new java.awt.Font("Segoe UI", 0, 11));

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

        descriptionScrollPane.setViewportView(descriptionArea);

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(30, 30, 30).addComponent(headingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(30, 30, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(30, 30, 30).addComponent(typeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(380, 380, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(160, 160, 160).addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(60, 60, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(30, 30, 30).addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(380, 380, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(160, 160, 160).addComponent(itemNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(60, 60, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(30, 30, 30).addComponent(categoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(380, 380, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(160, 160, 160).addComponent(categoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(60, 60, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(30, 30, 30).addComponent(descriptionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(380, 380, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(160, 160, 160).addComponent(descriptionScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(60, 60, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(30, 30, 30).addComponent(locationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(380, 380, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(160, 160, 160).addComponent(locationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(60, 60, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(30, 30, 30).addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(380, 380, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(160, 160, 160).addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(60, 60, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(160, 160, 160).addComponent(dateHintLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(60, 60, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(30, 30, 30).addComponent(photoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(380, 380, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(160, 160, 160).addComponent(choosePhotoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(250, 250, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(290, 290, 290).addComponent(photoNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(60, 60, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(160, 160, 160).addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(225, 225, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(325, 325, 325).addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(60, 60, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(30, 30, 30).addComponent(requiredLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(30, 30, Short.MAX_VALUE))
        );
        contentPanelLayout.setVerticalGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(22, 22, 22).addComponent(headingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(560, 560, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(82, 82, 82).addComponent(typeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(514, 514, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(78, 78, 78).addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(512, 512, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(126, 126, 126).addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(470, 470, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(122, 122, 122).addComponent(itemNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(468, 468, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(170, 170, 170).addComponent(categoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(426, 426, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(166, 166, 166).addComponent(categoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(424, 424, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(214, 214, 214).addComponent(descriptionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(382, 382, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(210, 210, 210).addComponent(descriptionScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(334, 334, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(304, 304, 304).addComponent(locationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(292, 292, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(300, 300, 300).addComponent(locationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(290, 290, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(348, 348, 348).addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(248, 248, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(344, 344, 344).addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(246, 246, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(376, 376, 376).addComponent(dateHintLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(224, 224, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(418, 418, 418).addComponent(photoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(178, 178, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(414, 414, 414).addComponent(choosePhotoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(174, 174, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(414, 414, 414).addComponent(photoNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(174, 174, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(492, 492, 492).addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(88, 88, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(492, 492, 492).addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(88, 88, Short.MAX_VALUE))
            .addGroup(contentPanelLayout.createSequentialGroup().addGap(558, 558, 558).addComponent(requiredLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(40, 40, Short.MAX_VALUE))
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

    private void choosePhotoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_choosePhotoButtonActionPerformed
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setDialogTitle("Choose an item photo");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Photos (PNG, JPG, GIF, BMP)", "png", "jpg", "jpeg", "gif", "bmp"));
        if (chooser.showOpenDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
            Path candidate = chooser.getSelectedFile().toPath();
            try {
                if (!java.nio.file.Files.isRegularFile(candidate)
                        || javax.imageio.ImageIO.read(candidate.toFile()) == null) {
                    throw new java.io.IOException("The selected file is not a readable image.");
                }
                selectedPhoto = candidate;
                photoNameLabel.setText(candidate.getFileName().toString());
                photoNameLabel.setToolTipText(candidate.toString());
            } catch (java.io.IOException ex) {
                JOptionPane.showMessageDialog(this, "Please choose a readable image file.",
                        "Invalid photo", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_choosePhotoButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        final LocalDate date;
        try {
            date = LocalDate.parse(dateField.getText().trim());
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid date in yyyy-MM-dd format.",
                    "Invalid date", JOptionPane.WARNING_MESSAGE);
            dateField.requestFocusInWindow();
            return;
        }
        final String type = (String) typeComboBox.getSelectedItem();
        final String name = itemNameField.getText();
        final String category = (String) categoryComboBox.getSelectedItem();
        final String description = descriptionArea.getText();
        if (locationComboBox.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Please select a campus location.",
                    "Location required", JOptionPane.WARNING_MESSAGE);
            locationComboBox.requestFocusInWindow();
            return;
        }
        final String location = (String) locationComboBox.getSelectedItem();
        final Path photo = selectedPhoto;
        setSaving(true);
        new javax.swing.SwingWorker<Path, Void>() {
            @Override protected Path doInBackground() throws Exception {
                return new ReportStore().save(type, name, category, description, location, date, photo);
            }

            @Override protected void done() {
                setSaving(false);
                try {
                    get();
                    JOptionPane.showMessageDialog(reportForm.this, "Your " + type.toLowerCase()
                            + " item report was saved.", "Report saved", JOptionPane.INFORMATION_MESSAGE);
                    returnToDashboard();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    showSaveError("Saving was interrupted. Please try again.");
                } catch (ExecutionException ex) {
                    Throwable cause = ex.getCause();
                    if (cause instanceof IllegalArgumentException) {
                        JOptionPane.showMessageDialog(reportForm.this, cause.getMessage(),
                                "Check your report", JOptionPane.WARNING_MESSAGE);
                    } else {
                        showSaveError("Could not save the report. Check that the data folder is writable "
                                + "and the selected photo is still available.");
                    }
                }
            }
        }.execute();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        returnToDashboard();
    }//GEN-LAST:event_backButtonActionPerformed

    private void navDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navDashboardButtonActionPerformed
        returnToDashboard();
    }//GEN-LAST:event_navDashboardButtonActionPerformed

    private void navBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navBrowseButtonActionPerformed
        new browseReports().setVisible(true);
        dispose();
    }//GEN-LAST:event_navBrowseButtonActionPerformed

    private void navMatchingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navMatchingButtonActionPerformed
        JOptionPane.showMessageDialog(this, "Matching Reports will be added after Browse Reports.");
    }//GEN-LAST:event_navMatchingButtonActionPerformed

    private void navAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navAccountButtonActionPerformed
        JOptionPane.showMessageDialog(this, "Account data screen will be added later.");
    }//GEN-LAST:event_navAccountButtonActionPerformed

    private void returnToDashboard() {
        new dashboard().setVisible(true);
        dispose();
    }

    private void setSaving(boolean saving) {
        saveButton.setEnabled(!saving);
        backButton.setEnabled(!saving);
        choosePhotoButton.setEnabled(!saving);
        navDashboardButton.setEnabled(!saving);
        navBrowseButton.setEnabled(!saving);
        navMatchingButton.setEnabled(!saving);
        navAccountButton.setEnabled(!saving);
        setDefaultCloseOperation(saving ? javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE
                : javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }

    private void showSaveError(String message) {
        JOptionPane.showMessageDialog(this, message, "Could not save", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new reportForm().setVisible(true));
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
    private javax.swing.JLabel typeLabel;
    private javax.swing.JComboBox<String> typeComboBox;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField itemNameField;
    private javax.swing.JLabel categoryLabel;
    private javax.swing.JComboBox<String> categoryComboBox;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JScrollPane descriptionScrollPane;
    private javax.swing.JTextArea descriptionArea;
    private javax.swing.JLabel locationLabel;
    private javax.swing.JComboBox<String> locationComboBox;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JTextField dateField;
    private javax.swing.JLabel dateHintLabel;
    private javax.swing.JLabel photoLabel;
    private javax.swing.JButton choosePhotoButton;
    private javax.swing.JLabel photoNameLabel;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton backButton;
    private javax.swing.JLabel requiredLabel;
    // End of variables declaration//GEN-END:variables
}
