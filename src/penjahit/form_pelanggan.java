/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package penjahit;
import koneksi.db_koneksi;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author Toshiba
 */
public class form_pelanggan extends javax.swing.JFrame {
//membuat clas DefaultTableModel
    private DefaultTableModel model;
    String id_pelanggan, nm_plg, jenis_kelamin, almt_plg, no_telp;
    
    public void loadData(){
        id_pelanggan=txtid_pelanggan.getText();
        nm_plg=txtnm_plg.getText();
        jenis_kelamin=(String) cmboxJenis.getSelectedItem();
        almt_plg=txtalmt_plg.getText();
        no_telp=txtno_telp.getText();
    }
    /**
     * Creates new form form_pelanggan
     */
    public form_pelanggan() {
        initComponents();
        
        //Memberi penamaan pada judul kolom pelanggan
        model = new DefaultTableModel ();
       pelanggan.setModel(model);
        model.addColumn("id_pelanggan");
        model.addColumn("nm_plg");
        model.addColumn("jenis_kelamin");
        model.addColumn("almt_plg");
        model.addColumn("no_telp");

        getData();
        
    }
    
    public void getData(){
        //menghapus isi table pelanggan
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        try{
            // membuat pemanggilan data pada table table pelanggan dari database
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
            String sql = "Select * from pelanggan";
            ResultSet res = stat.executeQuery(sql);
            
            //peneusuran baris pada table pelanggan dari database
            while(res.next()) {
                Object[] obj = new Object [5];
                obj[0] = res.getString("id_pelanggan");
                obj[1] = res.getString("nm_plg");
                obj[2] = res.getString("jenis_kelamin");
                obj[3] = res.getString("almt_plg");
                obj[4] = res.getString("no_telp");
                
                model.addRow(obj);
            }
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void saveData() {
    loadData();
    try{
        Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
        String sql = "Insert into pelanggan(id_pelanggan, nm_plg, jenis_kelamin, almt_plg, no_telp)" +"values ('"+id_pelanggan+"','"+nm_plg+"','"+jenis_kelamin+"','"+almt_plg+"','"+no_telp+"')";
   db_koneksi.getKoneksi().prepareStatement(sql).executeUpdate();
   getData();
   }catch(SQLException err) {
   JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void Reset(){
        id_pelanggan = "";
        nm_plg = "";
        jenis_kelamin = "";
        almt_plg = "";
        no_telp = "";
        txtid_pelanggan.setText(id_pelanggan);
        txtnm_plg.setText(nm_plg);
        txtalmt_plg.setText(almt_plg);
        txtno_telp.setText(no_telp);
    }
    
    public void dataSelect(){
    //deklarasi variabel    
        int i =pelanggan.getSelectedRow();
    
    //uji adakah data di tabel?
    if(i == -1){
        //tidak ada yang terpilih atau dipilih.
        return;
    }
    txtid_pelanggan.setText(""+model.getValueAt(i, 0));
    txtnm_plg.setText(""+model.getValueAt(i, 1));
    cmboxJenis.setSelectedItem(""+model.getValueAt(i, 2));
    txtalmt_plg.setText(""+model.getValueAt(i, 3));
    txtno_telp.setText(""+model.getValueAt(i, 4));
    }
    
    public void updateData(){
          //panggilfungsi load data
    loadData();

        //ujikoneksidaneksekusiperintah
    try{
            //test koneksi
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();

            //perintahsqluntuksimpan data
            String sql  =   "UPDATE pelanggan SET id_pelanggan = '"+ id_pelanggan  +"',"
                            + "nm_plg  = '"+ nm_plg +"',"
                            + "jenis_kelamin  = '"+ jenis_kelamin +"',"
                            + "almt_plg  = '"+ almt_plg +"',"
                            + "no_telp  = '"+ no_telp +"' WHERE id_pelanggan = '" + id_pelanggan +"'";
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
int pesan = JOptionPane.showConfirmDialog(null, "Hapus Data Dengan id_pelanggan "+ id_pelanggan +"?","KONFIRMASI", JOptionPane.OK_CANCEL_OPTION);

        //jikapenggunamemilih OK lanjutkan proses hapus data
if(pesan == JOptionPane.OK_OPTION){
            //ujikoneksi
try{
                //bukakoneksike database
                Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();

                //perintahhapus data
                String sql = "DELETE FROM pelanggan WHERE id_pelanggan='"+ id_pelanggan +"'";
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        btnKeluar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pelanggan = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtnm_plg = new javax.swing.JTextField();
        cmboxJenis = new javax.swing.JComboBox<>();
        txtid_pelanggan = new javax.swing.JTextField();
        txtalmt_plg = new javax.swing.JTextField();
        txtno_telp = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnDelete = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();
        btncetak1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Form_Pelanggan");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Jenis Kelamin");

        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Alamat Pelangan");

        pelanggan.setModel(new javax.swing.table.DefaultTableModel(
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
        pelanggan.setName(""); // NOI18N
        pelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pelangganMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(pelanggan);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("No. Telp");

        txtnm_plg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtnm_plg.setName(""); // NOI18N

        cmboxJenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Laki-Laki", "Perempuan" }));
        cmboxJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmboxJenisActionPerformed(evt);
            }
        });

        txtid_pelanggan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtid_pelanggan.setName(""); // NOI18N

        txtalmt_plg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtalmt_plg.setName(""); // NOI18N

        txtno_telp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtno_telp.setName(""); // NOI18N

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Data Pelanggan");

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Id_Pelanggan");

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Nama Pelanggan");

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btncetak1.setText("Cetak");
        btncetak1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncetak1ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setText("Jasa Jahit eRro");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Jl. Kenanga No. 18 Gemantar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6))
                                        .addGap(34, 34, 34))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(56, 56, 56)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmboxJenis, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtid_pelanggan)
                                    .addComponent(txtnm_plg, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                    .addComponent(txtalmt_plg)
                                    .addComponent(txtno_telp)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDelete)
                                .addGap(18, 18, 18)
                                .addComponent(btnReset)
                                .addGap(18, 18, 18)
                                .addComponent(btnUpdate)
                                .addGap(18, 18, 18)
                                .addComponent(btnKeluar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btncetak1))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel1)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtid_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtnm_plg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(jLabel4)
                        .addGap(39, 39, 39))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cmboxJenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtalmt_plg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtno_telp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete)
                    .addComponent(btnReset)
                    .addComponent(btnUpdate)
                    .addComponent(btnSave)
                    .addComponent(btnKeluar)
                    .addComponent(btncetak1))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void pelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pelangganMouseClicked
        // TODO add your handling code here:
        dataSelect();
    }//GEN-LAST:event_pelangganMouseClicked

    private void cmboxJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmboxJenisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmboxJenisActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        saveData();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        deleteData();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateData();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        Reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btncetak1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncetak1ActionPerformed
        // TODO add your handling code here:
        try{
            JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("report_pelanggan.jasper"), null,db_koneksi.getKoneksi());
            JasperViewer.viewReport(jp, false);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_btncetak1ActionPerformed

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
            java.util.logging.Logger.getLogger(form_pelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_pelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_pelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_pelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_pelanggan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btncetak1;
    private javax.swing.JComboBox<String> cmboxJenis;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable pelanggan;
    private javax.swing.JTextField txtalmt_plg;
    private javax.swing.JTextField txtid_pelanggan;
    private javax.swing.JTextField txtnm_plg;
    private javax.swing.JTextField txtno_telp;
    // End of variables declaration//GEN-END:variables
}
