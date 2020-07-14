/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package penjahit;
import koneksi.db_koneksi;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author Rofi Qoriah
 */
public class form_transaksi extends javax.swing.JFrame {
//membuat clas DefaultTableModel
    private DefaultTableModel model;
    String kd_transaksi, id_pelanggan, nm_plg, id_penjahit, nm_jahit, model_busana, jenis_bahan, tgl_jahit, tgl_ambil;
    
    public void loadData(){
        kd_transaksi=txtkd_transaksi.getText();
        id_pelanggan=(String)jComboBox1.getSelectedItem();
        nm_plg=txtnm_plg.getText();
        id_penjahit=(String)jComboBox2.getSelectedItem();
        nm_jahit=txtnm_jahit.getText();
        model_busana=txtmodel_busana.getText();
        jenis_bahan=(String)jComboBox3.getSelectedItem();
        tgl_jahit=jDateChooser1.getDateFormatString();
        tgl_ambil=jDateChooser3.getDateFormatString();
    }
    
    public void Reset(){
        kd_transaksi = "";
        id_pelanggan = "";
        nm_plg = "";
        id_penjahit = "";
        nm_jahit = "";
        model_busana = "";
        jenis_bahan = "";
        tgl_jahit = "";
        tgl_ambil = "";
        txtkd_transaksi.setText(kd_transaksi);
        txtnm_plg.setText(nm_plg);
        txtnm_jahit.setText(nm_jahit);
        txtmodel_busana.setText(model_busana);
        jDateChooser1.setDateFormatString(tgl_jahit);
        jDateChooser3.setDateFormatString(tgl_ambil);
    }
    
    public void dataSelect(){
    //deklarasi variabel    
        int i =transaksi.getSelectedRow();
    
    //uji adakah data di tabel?
    if(i == -1){
        //tidak ada yang terpilih atau dipilih.
        return;
    }
    txtkd_transaksi.setText(""+model.getValueAt(i, 0));
    jComboBox1.setSelectedItem(""+model.getValueAt(i, 1));
    txtnm_plg.setText(""+model.getValueAt(i,2));
    jComboBox2.setSelectedItem(""+model.getValueAt(i, 3));
    txtnm_jahit.setText(""+model.getValueAt(i,4));
    txtmodel_busana.setText(""+model.getValueAt(i, 5));
    jComboBox3.setSelectedItem(""+model.getValueAt(i, 6));
    jDateChooser1.setDateFormatString(""+model.getValueAt(i, 7));
    jDateChooser3.setDateFormatString(""+model.getValueAt(i, 8));
    }
    
    public void updateData(){
          //panggilfungsi load data
    loadData();

        //ujikoneksidaneksekusiperintah
    try{
            //test koneksi
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();

            //perintahsqluntuksimpan data
            String sql  =   "UPDATE transaksi SET kd_transaksi = '"+ kd_transaksi  +"',"
                            + "id_pelanggan  = '"+ id_pelanggan +"',"
                            + "nm_plg  = '"+ nm_plg +"',"
                            + "id_penjahit  = '"+ id_penjahit +"',"
                            + "nm_jahit  = '"+ nm_jahit +"',"
                            + "model_busana  = '"+ model_busana +"',"
                            + "jenis_bahan  = '"+ jenis_bahan +"',"
                            + "tgl_jahit  = '"+ tgl_jahit +"',"
                            + "tgl_ambil  = '"+ tgl_ambil +"' WHERE kd_transaksi = '" + kd_transaksi +"'";
    db_koneksi.getKoneksi().prepareStatement(sql).executeUpdate();

            //ambil data
    getData();

            //memanggil class Reset()agar setelah update berhasil data yang terdapatpadakomponen- komponenlangsungdikosongkan
    Reset();
    JOptionPane.showMessageDialog(null, "Update berhasil...");
    }catch(SQLException er){
    JOptionPane.showMessageDialog(null, er.getMessage());
        }
    }
    
    public void deleteData(){
        //panggil fungsi ambil data
loadData(); 

        //Beri peringatan sebelum melakukan penghapusan data
int pesan = JOptionPane.showConfirmDialog(null, "Hapus Data Dengan kd_transaksi "+ kd_transaksi +"?","KONFIRMASI", JOptionPane.OK_CANCEL_OPTION);

        //jikapenggunamemilih OK lanjutkan proses hapus data
if(pesan == JOptionPane.OK_OPTION){
            //ujikoneksi
try{
                //bukakoneksike database
                Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();

                //perintahhapus data
                String sql = "DELETE FROM transaksi WHERE kd_transaksi='"+ kd_transaksi +"'";
db_koneksi.getKoneksi().prepareStatement(sql).executeUpdate();

                //fungsiambil data
getData();

                //fungsi reset data
Reset();
JOptionPane.showMessageDialog(null, "Delete Berhasil Horeeyey...");
}catch(SQLException er){
JOptionPane.showMessageDialog(null, er.getMessage());
            }
        }
    }
    /**
     * Creates new form form_transaksi
     */
    public form_transaksi() {
        initComponents();
        comboboxpelanggan();
        comboboxpenjahit();
        
        //Memberi penamaan pada judul kolom transaksi
        model = new DefaultTableModel ();
       transaksi.setModel(model);
        model.addColumn("kd_transaksi");
        model.addColumn("id_pelanggan");
        model.addColumn("nm_plg");
        model.addColumn("id_penjahit");
        model.addColumn("nm_jahit");
        model.addColumn("model_busana");
        model.addColumn("jenis_bahan");
        model.addColumn("tgl_jahit");
        model.addColumn("tgl_ambil");

        getData();
    }
    
    public void getData(){
        //menghapus isi table transaksi
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        try{
            // membuat pemanggilan data pada table table transaksi dari database
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
            String sql = "Select * from transaksi";
            ResultSet res = stat.executeQuery(sql);
            
            //peneusuran baris pada table transaksi dari database
            while(res.next()) {
                Object[] obj = new Object [9];
                obj[0] = res.getString("kd_transaksi");
                obj[1] = res.getString("id_pelanggan");
                obj[2] = res.getString("nm_plg");
                obj[3] = res.getString("id_penjahit");
                obj[4] = res.getString("nm_jahit");
                obj[5] = res.getString("model_busana");
                obj[6] = res.getString("jenis_bahan");
                obj[7] = res.getString("tgl_jahit");
                obj[8] = res.getString("tgl_ambil");
                
                model.addRow(obj);
            }
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void comboboxpelanggan(){
        jComboBox1.removeAllItems();
        try{
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
            String sql = "Select * from pelanggan";
            ResultSet res = stat.executeQuery(sql);
            
             while(res.next()){
                 Object[] obj = new Object[1];
                 obj[0] = res.getString("id_pelanggan");
                 jComboBox1.addItem(obj[0].toString());
        }
            }catch(SQLException err){
                JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    
    public void comboboxpenjahit(){
        jComboBox2.removeAllItems();
        try{
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
            String sql = "Select * from penjahit";
            ResultSet res = stat.executeQuery(sql);
            
             while(res.next()){
                 Object[] obj = new Object[1];
                 obj[0] = res.getString("id_penjahit");
                 jComboBox2.addItem(obj[0].toString());
        }
            }catch(SQLException err){
                JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void saveData() {
    loadData();
    String tampilan = "yyyy-MM-dd";
    SimpleDateFormat fm = new SimpleDateFormat(tampilan);
    String tgl_jahit=String.valueOf(fm.format(jDateChooser1.getDate()));
    String tgl_ambil=String.valueOf(fm.format(jDateChooser3.getDate()));
    
    try{
        Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
        String sql = "Insert into transaksi(kd_transaksi, id_pelanggan, nm_plg, id_penjahit, nm_jahit, model_busana, jenis_bahan, tgl_jahit, tgl_ambil)" +"values ('"+kd_transaksi+"','"+id_pelanggan+"','"+nm_plg+"','"+id_penjahit+"','"+nm_jahit+"','"+model_busana+"',"
                + "'"+jenis_bahan+"','"+tgl_jahit+"','"+tgl_ambil+"')";
   db_koneksi.getKoneksi().prepareStatement(sql).executeUpdate();
   getData();
   }catch(SQLException err) {
   JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnKeluar = new javax.swing.JButton();
        txtkd_transaksi = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        transaksi = new javax.swing.JTable();
        txtmodel_busana = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtnm_plg = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtnm_jahit = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        btnCetak = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Form_Transaksi");

        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        txtkd_transaksi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtkd_transaksi.setName(""); // NOI18N

        transaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        transaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                transaksiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(transaksi);

        txtmodel_busana.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtmodel_busana.setName(""); // NOI18N

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Kode Transaksi");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Id Pelanggan");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Id Penjahit");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Model Busana");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Jenis Bahan");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Tanggal Menjahit");

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Tanggal Mengambil");

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jComboBox2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox2MouseClicked(evt);
            }
        });
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Nama Pelanggan");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Nama Penjahit");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Drill", "Katun", "Broklat", "Batik", "Velvet", "Balotely", "Mocsrape" }));

        btnCetak.setText("Cetak");
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel16.setText("Jasa Jahit eRro");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setText("Jl. Kenanga No. 18 Gemantar");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Data Transaksi");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSave)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete)
                                .addGap(18, 18, 18)
                                .addComponent(btnReset)
                                .addGap(18, 18, 18)
                                .addComponent(btnUpdate)
                                .addGap(18, 18, 18)
                                .addComponent(btnKeluar)
                                .addGap(18, 18, 18)
                                .addComponent(btnCetak))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 205, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtkd_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtnm_plg)
                                    .addComponent(txtnm_jahit)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(105, 105, 105)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel8))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtmodel_busana)
                                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel17)
                            .addComponent(jLabel9)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtkd_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtmodel_busana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel7)
                            .addComponent(txtnm_plg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtnm_jahit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSave)
                        .addComponent(btnDelete)
                        .addComponent(btnReset)
                        .addComponent(btnUpdate))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnKeluar)
                        .addComponent(btnCetak)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(165, 165, 165))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateData();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        saveData();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        deleteData();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        Reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void transaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_transaksiMouseClicked
        // TODO add your handling code here:
        dataSelect();
    }//GEN-LAST:event_transaksiMouseClicked

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
        // TODO add your handling code here:
        try{
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
            String sql = "select *from pelanggan where id_pelanggan = '"+jComboBox1.getSelectedItem().toString()+"'";
            ResultSet res = stat.executeQuery(sql);
            
             while(res.next()){
                 Object[] obj = new Object[1];
                 obj[0] = res.getString("nm_plg");
                 txtnm_plg.setText(obj[0].toString());
             }
              }catch(SQLException err){
                  JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jComboBox2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox2MouseClicked
        // TODO add your handling code here:
        try{
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
            String sql = "select *from penjahit where id_penjahit = '"+jComboBox2.getSelectedItem().toString()+"'";
            ResultSet res = stat.executeQuery(sql);
            
             while(res.next()){
                 Object[] obj = new Object[1];
                 obj[0] = res.getString("nm_jahit");
                 txtnm_jahit.setText(obj[0].toString());
             }
              }catch(SQLException err){
                  JOptionPane.showMessageDialog(null, err.getMessage());
        }
    
    }//GEN-LAST:event_jComboBox2MouseClicked

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        // TODO add your handling code here:
        try{
            JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("report_transaksi.jasper"), null,db_koneksi.getKoneksi());
            JasperViewer.viewReport(jp, false);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_btnCetakActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(form_transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable transaksi;
    private javax.swing.JTextField txtkd_transaksi;
    private javax.swing.JTextField txtmodel_busana;
    private javax.swing.JTextField txtnm_jahit;
    private javax.swing.JTextField txtnm_plg;
    // End of variables declaration//GEN-END:variables
}
