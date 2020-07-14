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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author Toshiba
 */
public class form_penjahit extends javax.swing.JFrame {
//membuat clas DefaultTableModel
    private DefaultTableModel model;
    String id_penjahit, nm_jahit, username, password, almt_jahit, no_telp;
    
    public void loadData(){
        id_penjahit=txtid_penjahit.getText();
        nm_jahit=txtnm_jahit.getText();
        username=txtuser.getText();
        password=txtpass.getText();
        almt_jahit=txtalmt_jahit.getText();
        no_telp=txtno_telp.getText();
    }
    /**
     * Creates new form form_penjahit
     */
    public form_penjahit() {
        initComponents();
        
        //Memberi penamaan pada judul kolom penjahit
        model = new DefaultTableModel ();
       penjahit.setModel(model);
        model.addColumn("id_penjahit");
        model.addColumn("nm_jahit");
        model.addColumn("username");
        model.addColumn("password");
        model.addColumn("almt_jahit");
        model.addColumn("no_telp");

        getData();
    }
    
    public void getData(){
        //menghapus isi table penjahit
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        try{
            // membuat pemanggilan data pada table table penjahit dari database
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
            String sql = "Select * from penjahit";
            ResultSet res = stat.executeQuery(sql);
            
            //peneusuran baris pada table penjahit dari database
            while(res.next()) {
                Object[] obj = new Object [6];
                obj[0] = res.getString("id_penjahit");
                obj[1] = res.getString("nm_jahit");
                obj[2] = res.getString("username");
                obj[3] = res.getString("password");
                obj[4] = res.getString("almt_jahit");
                obj[5] = res.getString("no_telp");
                
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
        String sql = "Insert into penjahit(id_penjahit, nm_jahit, username, password, almt_jahit, no_telp)" +"values ('"+id_penjahit+"','"+nm_jahit+"','"+username+"','"+password+"','"+almt_jahit+"','"+no_telp+"')";
   db_koneksi.getKoneksi().prepareStatement(sql).executeUpdate();
   getData();
   }catch(SQLException err) {
   JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void Reset(){
        id_penjahit = "";
        nm_jahit = "";
        username = "";
        password = "";
        almt_jahit = "";
        no_telp = "";
        txtid_penjahit.setText(id_penjahit);
        txtnm_jahit.setText(nm_jahit);
        txtuser.setText(username);
        txtpass.setText(password);
        txtalmt_jahit.setText(almt_jahit);
        txtno_telp.setText(no_telp);
    }
    
    public void dataSelect(){
    //deklarasi variabel    
        int i =penjahit.getSelectedRow();
    
    //uji adakah data di tabel?
    if(i == -1){
        //tidak ada yang terpilih atau dipilih.
        return;
    }
    txtid_penjahit.setText(""+model.getValueAt(i, 0));
    txtnm_jahit.setText(""+model.getValueAt(i, 1));
    txtuser.setText(""+model.getValueAt(i, 2));
    txtpass.setText(""+model.getValueAt(i, 3));
    txtalmt_jahit.setText(""+model.getValueAt(i, 4));
    txtno_telp.setText(""+model.getValueAt(i, 5));
    }
    
    public void updateData(){
          //panggil fungsi load data
    loadData();

        //uji koneksi dan eksekusi perintah
    try{
            //test koneksi
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();

            //perintah sql untuk simpan data
            String sql  =   "UPDATE penjahit SET id_penjahit = '"+ id_penjahit  +"',"
                            + "nm_jahit  = '"+ nm_jahit +"',"
                            + "username  = '"+ username +"',"
                            + "password  = '"+ password +"',"
                            + "almt_jahit  = '"+ almt_jahit +"',"
                            + "no_telp  = '"+ no_telp +"' WHERE id_penjahit = '" + id_penjahit +"'";
    db_koneksi.getKoneksi().prepareStatement(sql).executeUpdate();

            //ambil data
    getData();

            //memanggil class Reset()agar setelah update berhasil data yang terdapatpadakomponen- komponen langsung dikosongkan
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
int pesan = JOptionPane.showConfirmDialog(null, "Hapus Data Dengan id_penjahit "+ id_penjahit +"?","KONFIRMASI", JOptionPane.OK_CANCEL_OPTION);

        //jikapenggunamemilih OK lanjutkan proses hapus data
if(pesan == JOptionPane.OK_OPTION){
            //ujikoneksi
try{
                //bukakoneksike database
                Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();

                //perintahhapus data
                String sql = "DELETE FROM penjahit WHERE id_penjahit='"+ id_penjahit +"'";
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

        txtalmt_jahit = new javax.swing.JTextField();
        txtno_telp = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        penjahit = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtid_penjahit = new javax.swing.JTextField();
        txtnm_jahit = new javax.swing.JTextField();
        txtpass = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtuser = new javax.swing.JTextField();
        btnCetak = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Form_Penjahit");

        txtalmt_jahit.setName(""); // NOI18N

        txtno_telp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtno_telp.setName(""); // NOI18N

        btnSave.setText("Save");
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveMouseClicked(evt);
            }
        });
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

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        penjahit.setModel(new javax.swing.table.DefaultTableModel(
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
        penjahit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                penjahitMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(penjahit);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Id_Penjahit");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Nama Penjahit");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Password");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Alamat");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("No. Telp");

        txtid_penjahit.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtid_penjahit.setName(""); // NOI18N

        txtnm_jahit.setName(""); // NOI18N

        txtpass.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtpass.setName(""); // NOI18N
        txtpass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpassActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Username");

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

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Data Penjahit");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addGap(18, 18, 18)
                        .addComponent(btnCetak))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtid_penjahit, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtnm_jahit, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtpass, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE))
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtno_telp, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtuser, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtalmt_jahit, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 647, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel8)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtid_penjahit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtnm_jahit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtuser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtpass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtalmt_jahit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtno_telp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete)
                    .addComponent(btnReset)
                    .addComponent(btnUpdate)
                    .addComponent(btnSave)
                    .addComponent(btnKeluar)
                    .addComponent(btnCetak))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveMouseClicked

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

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void penjahitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_penjahitMouseClicked
        // TODO add your handling code here:
        dataSelect();
    }//GEN-LAST:event_penjahitMouseClicked

    private void txtpassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpassActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        saveData();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        // TODO add your handling code here:
        try{
            JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("report_penjahit.jasper"), null,db_koneksi.getKoneksi());
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
            java.util.logging.Logger.getLogger(form_penjahit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_penjahit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_penjahit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_penjahit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_penjahit().setVisible(true);
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
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable penjahit;
    private javax.swing.JTextField txtalmt_jahit;
    private javax.swing.JTextField txtid_penjahit;
    private javax.swing.JTextField txtnm_jahit;
    private javax.swing.JTextField txtno_telp;
    private javax.swing.JTextField txtpass;
    private javax.swing.JTextField txtuser;
    // End of variables declaration//GEN-END:variables
}
