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
public class form_pembayaran extends javax.swing.JFrame {
//membuat clas DefaultTableModel
    private DefaultTableModel model;
    String id_pembayaran, kd_transaksi, id_pelanggan, nm_plg, id_penjahit, nm_jahit, model_busana, tgl_ambil;
    double harga_busana, jasa_jahit, jml_bayar, uang_bayar, uang_kembali;
    
    public void loadData(){
        id_pembayaran=txtid_pembayaran.getText();
        kd_transaksi=txtkd_transaksi.getText();
        id_pelanggan=txtid_pelanggan.getText();
        nm_plg=txtnm_plg.getText();
        id_penjahit=txtid_penjahit.getText();
        nm_jahit=txtnm_jahit.getText();
        model_busana=txtmodel_busana.getText();
        tgl_ambil=jDateChooser3.getDateFormatString();
        harga_busana=Double.parseDouble(txtharga_busana.getText());
        jasa_jahit=Double.parseDouble(txtjasa_jahit.getText());
        jml_bayar=Double.parseDouble(txtjml_bayar.getText());
        uang_bayar=Double.parseDouble(txtuang_bayar.getText());
        uang_kembali=Double.parseDouble(txtuang_kembali.getText());
    }
    
    public void Reset(){
        id_pembayaran = "";
        kd_transaksi = "";
        id_pelanggan = "";
        nm_plg = "";
        id_penjahit = "";
        nm_jahit = "";
        model_busana = "";
        tgl_ambil = "";
        harga_busana = 0;
        jasa_jahit = 0;
        jml_bayar = 0;
        uang_bayar = 0;
        uang_kembali = 0;
        txtid_pembayaran.setText(id_pembayaran);
        txtkd_transaksi.setText(kd_transaksi);
        txtid_pelanggan.setText(id_pelanggan);
        txtnm_plg.setText(nm_plg);
        txtid_penjahit.setText(id_penjahit);
        txtnm_jahit.setText(nm_jahit);
        txtmodel_busana.setText(model_busana);
        jDateChooser3.setDateFormatString(tgl_ambil);
        txtharga_busana.setText("");
        txtjasa_jahit.setText("");
        txtjml_bayar.setText("");
        txtuang_bayar.setText("");
        txtuang_kembali.setText("");
    }
    
    public void dataSelect(){
    //deklarasi variabel    
        int i = pembayaran.getSelectedRow();
    
    //uji adakah data di tabel?
    if(i == -1){
        //tidak ada yang terpilih atau dipilih.
        return;
    }
    txtid_pembayaran.setText(""+model.getValueAt(i, 0));
    txtkd_transaksi.setText(""+model.getValueAt(i, 1));
    txtid_pelanggan.setText(""+model.getValueAt(i, 2));
    txtnm_plg.setText(""+model.getValueAt(i, 3));
    txtid_penjahit.setText(""+model.getValueAt(i, 4));
    txtnm_jahit.setText(""+model.getValueAt(i, 5));
    txtmodel_busana.setText(""+model.getValueAt(i, 6));
    jDateChooser3.setDateFormatString(""+model.getValueAt(i, 7));
    txtharga_busana.setText(""+model.getValueAt(i, 8));
    txtjasa_jahit.setText(""+model.getValueAt(i, 9));
    txtjml_bayar.setText(""+model.getValueAt(i, 10));
    txtuang_bayar.setText(""+model.getValueAt(i, 11));
    txtuang_kembali.setText(""+model.getValueAt(i, 12));
    }
    
    public void updateData(){
          //panggilfungsi load data
    loadData();

        //ujikoneksidaneksekusiperintah
    try{
            //test koneksi
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();

            //perintahsqluntuksimpan data
            String sql  =   "UPDATE pembayaran SET id_pembayaran = '"+ id_pembayaran  +"',"
                            + "kd_transaksi  = '"+ kd_transaksi +"',"
                            + "id_pelanggan  = '"+ id_pelanggan +"',"
                            + "nm_plg  = '"+ nm_plg +"',"
                            + "id_penjahit  = '"+ id_penjahit +"',"
                            + "nm_jahit  = '"+ nm_jahit +"',"
                            + "model_busana  = '"+ model_busana +"',"
                            + "tgl_ambil  = '"+ tgl_ambil +"',"
                            + "harga_busana  = '"+ harga_busana +"',"
                            + "jasa_jahit  = '"+ jasa_jahit +"',"
                            + "jml_bayar  = '"+ jml_bayar +"',"
                            + "uang_bayar  = '"+ uang_bayar +"',"
                            + "uang_kembali  = '"+ uang_kembali +"' WHERE id_pembayaran = '" + id_pembayaran +"'";
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
int pesan = JOptionPane.showConfirmDialog(null, "Hapus Data Dengan id_pembayaran "+ id_pembayaran +"?","KONFIRMASI", JOptionPane.OK_CANCEL_OPTION);

        //jikapenggunamemilih OK lanjutkan proses hapus data
if(pesan == JOptionPane.OK_OPTION){
            //ujikoneksi
try{
                //bukakoneksike database
                Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();

                //perintahhapus data
                String sql = "DELETE FROM pembayaran WHERE id_pembayaran='"+ id_pembayaran +"'";
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
     * Creates new form form_pembayaran
     */
    public form_pembayaran() {
        initComponents();
        
        //Memberi penamaan pada judul kolom pembayaran
        model = new DefaultTableModel ();
       pembayaran.setModel(model);
        model.addColumn("id_pembayaran");
        model.addColumn("kd_transaksi");
        model.addColumn("id_pelanggan");
        model.addColumn("nm_plg");
        model.addColumn("id_penjahit");
        model.addColumn("nm_jahit");
        model.addColumn("model_busana");
        model.addColumn("tgl_ambil");
        model.addColumn("harga_busana");
        model.addColumn("jasa_jahit");
        model.addColumn("jml_bayar");
        model.addColumn("uang_bayar");
        model.addColumn("uang_kembali");

        getData();
    }
    
    public void getData(){
        //menghapus isi table pembayaran
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        try{
            // membuat pemanggilan data pada table table pembayaran dari database
            Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
            String sql = "Select * from pembayaran";
            ResultSet res = stat.executeQuery(sql);
            
            //penelusuran baris pada table pembayaran dari database
            while(res.next()) {
                Object[] obj = new Object [13];
                obj[0] = res.getString("id_pembayaran");
                obj[1] = res.getString("kd_transaksi");
                obj[2] = res.getString("id_pelanggan");
                obj[3] = res.getString("nm_plg");
                obj[4] = res.getString("id_penjahit");
                obj[5] = res.getString("nm_jahit");
                obj[6] = res.getString("model_busana");
                obj[7] = res.getString("tgl_ambil");
                obj[8] = res.getString("harga_busana");
                obj[9] = res.getString("jasa_jahit");
                obj[10] = res.getString("jml_bayar");
                obj[11] = res.getString("uang_bayar");
                obj[12] = res.getString("uang_kembali");
                
                model.addRow(obj);
            }
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void saveData() {
    loadData();
    String tampilan = "yyyy-MM-dd";
    SimpleDateFormat fm = new SimpleDateFormat(tampilan);
    String tgl_ambil=String.valueOf(fm.format(jDateChooser3.getDate()));
    
    try{
        Statement stat = (Statement) db_koneksi.getKoneksi().createStatement();
        String sql = "Insert into pembayaran(id_pembayaran, kd_transaksi, id_pelanggan, nm_plg, id_penjahit, nm_jahit, model_busana, tgl_ambil, harga_busana, jasa_jahit, jml_bayar, uang_bayar, uang_kembali)" +"values ('"+id_pembayaran+"','"+kd_transaksi+"','"+id_pelanggan+"','"+nm_plg+"','"+id_penjahit+"','"+nm_jahit+"','"+model_busana+"','"+tgl_ambil+"','"+harga_busana+"','"+jasa_jahit+"','"+jml_bayar+"','"+uang_bayar+"','"+uang_kembali+"')";
   db_koneksi.getKoneksi().prepareStatement(sql).executeUpdate();
   getData();
   }catch(SQLException err) {
   JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void dataTransaksi(){   
        try{
            //tes koneksi
            java.sql.Statement stat = (java.sql.Statement) db_koneksi.getKoneksi().createStatement();
           
            //perintah sql untuk membaca data dari tabel produk
            String sql = "SELECT * FROM transaksi WHERE kd_transaksi = '"+ txtkd_transaksi.getText() +"'";
            ResultSet res = stat.executeQuery(sql);
                        
            //baca data dan tampilkan pada text produk dan harga
            while(res.next()){
                //membuat obyek berjenis array
               txtid_pelanggan.setText(res.getString("id_pelanggan"));
               txtnm_plg.setText(res.getString("nm_plg"));
               txtid_penjahit.setText(res.getString("id_penjahit"));
               txtnm_jahit.setText(res.getString("nm_jahit"));
               txtmodel_busana.setText(res.getString("model_busana"));
               jDateChooser3.setDate(res.getDate("tgl_ambil"));
            }
        }catch(SQLException err){
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

        jLabel10 = new javax.swing.JLabel();
        txtnm_plg = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtnm_jahit = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        pembayaran = new javax.swing.JTable();
        txtmodel_busana = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtid_pelanggan = new javax.swing.JTextField();
        txtid_penjahit = new javax.swing.JTextField();
        txtharga_busana = new javax.swing.JTextField();
        txtjasa_jahit = new javax.swing.JTextField();
        txtjml_bayar = new javax.swing.JTextField();
        txtuang_bayar = new javax.swing.JTextField();
        txtuang_kembali = new javax.swing.JTextField();
        txtid_pembayaran = new javax.swing.JTextField();
        txtkd_transaksi = new javax.swing.JTextField();
        btnCetak = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Form_Pembayaran");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Id Pelanggan");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Nama Penjahit");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Kode Transaksi");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Id Pembayaran");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Nama Pelanggan");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Harga Busana");

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Tanggal Mengambil");

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

        pembayaran.setModel(new javax.swing.table.DefaultTableModel(
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
        pembayaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pembayaranMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(pembayaran);

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

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Id Penjahit");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Model Busana");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Jasa Jahit");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Jumlah Bayar");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Uang Bayar");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Uang Kembali");

        txtid_pelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtid_pelangganKeyReleased(evt);
            }
        });

        txtjasa_jahit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtjasa_jahitKeyReleased(evt);
            }
        });

        txtuang_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtuang_bayarKeyReleased(evt);
            }
        });

        txtkd_transaksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtkd_transaksiKeyReleased(evt);
            }
        });

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

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Data Pembayaran");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addGap(25, 25, 25))
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtkd_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(178, 178, 178))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(108, 108, 108)
                                        .addComponent(txtid_pembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtnm_plg, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtid_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtid_penjahit, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtnm_jahit, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtmodel_busana, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(95, 95, 95)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14)
                            .addComponent(jLabel6)
                            .addComponent(jLabel13)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtjml_bayar)
                            .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtuang_kembali)
                            .addComponent(txtuang_bayar)
                            .addComponent(txtharga_busana)
                            .addComponent(txtjasa_jahit))
                        .addGap(155, 155, 155))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel2)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                .addComponent(btnCetak)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9)
                            .addComponent(txtid_pembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtharga_busana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtkd_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtjasa_jahit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtid_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtjml_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(txtnm_plg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtid_penjahit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtuang_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(txtuang_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtnm_jahit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtmodel_busana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(168, 168, 168)))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSave)
                        .addComponent(btnDelete)
                        .addComponent(btnReset)
                        .addComponent(btnUpdate))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnKeluar)
                        .addComponent(btnCetak)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void pembayaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pembayaranMouseClicked
        // TODO add your handling code here:
        dataSelect();
    }//GEN-LAST:event_pembayaranMouseClicked

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        saveData();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        deleteData();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtid_pelangganKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtid_pelangganKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtid_pelangganKeyReleased

    private void txtkd_transaksiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtkd_transaksiKeyReleased
        // TODO add your handling code here:
        dataTransaksi();
    }//GEN-LAST:event_txtkd_transaksiKeyReleased

    private void txtjasa_jahitKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtjasa_jahitKeyReleased
        // TODO add your handling code here:
        harga_busana = Double.parseDouble(txtharga_busana.getText());
        jasa_jahit = Double.parseDouble(txtjasa_jahit.getText());
        jml_bayar = (harga_busana + jasa_jahit);
        
        txtjml_bayar.setText(String.valueOf(jml_bayar));
        
    }//GEN-LAST:event_txtjasa_jahitKeyReleased

    private void txtuang_bayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtuang_bayarKeyReleased
        // TODO add your handling code here:
        jml_bayar = Double.parseDouble(txtjml_bayar.getText());
        uang_bayar = Double.parseDouble(txtuang_bayar.getText());
        uang_kembali = (uang_bayar - jml_bayar);
        
        txtuang_kembali.setText(String.valueOf(uang_kembali));
    }//GEN-LAST:event_txtuang_bayarKeyReleased

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        // TODO add your handling code here:
        try{
            JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("repor_pembayaran.jasper"), null,db_koneksi.getKoneksi());
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
            java.util.logging.Logger.getLogger(form_pembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_pembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_pembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_pembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_pembayaran().setVisible(true);
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
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JTable pembayaran;
    private javax.swing.JTextField txtharga_busana;
    private javax.swing.JTextField txtid_pelanggan;
    private javax.swing.JTextField txtid_pembayaran;
    private javax.swing.JTextField txtid_penjahit;
    private javax.swing.JTextField txtjasa_jahit;
    private javax.swing.JTextField txtjml_bayar;
    private javax.swing.JTextField txtkd_transaksi;
    private javax.swing.JTextField txtmodel_busana;
    private javax.swing.JTextField txtnm_jahit;
    private javax.swing.JTextField txtnm_plg;
    private javax.swing.JTextField txtuang_bayar;
    private javax.swing.JTextField txtuang_kembali;
    // End of variables declaration//GEN-END:variables
}
