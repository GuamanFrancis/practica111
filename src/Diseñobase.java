import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class Diseñobase extends JFrame{
    private JButton button1;
    private JCheckBox checkBox1;
    private JPanel panelventana;
    private JTextField nom;
    private JTextField eda;
    private JTextField primeranota;
    private JTextField segundanota;
    private JButton verDatosButton;

    public Diseñobase() {

        super("Bienvenido");
        setContentPane(panelventana);
        setSize(400,500);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    try{
                        ingreso();
                    }catch (SQLException ex){
                        System.out.println(ex.getMessage());
                    }



            }
        });
        verDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    verdatos();
                }catch (SQLException ex){
                    System.out.println(ex.getMessage());
                }


            }
        });
    }

    public  void ingreso()throws SQLException{
                String nombre = nom.getText();
                String edad = eda.getText();
                Double nota1= Double.parseDouble(primeranota.getText());
                Double nota2= Double.parseDouble(segundanota.getText());
                Connection connection=conexion();
                String sql = "INSERT INTO estudiantes(nombre,edad,nota1,nota2)values(?,?,?,?)";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1,nombre);
                pstmt.setInt(2, Integer.parseInt(edad));
                pstmt.setDouble(3,nota1);
                pstmt.setDouble(4,nota2);
                int rowsAffected = pstmt.executeUpdate();
                if ((rowsAffected>0 )){
                    JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
                }
                pstmt.close();
                connection.close();

    }

    public Connection conexion()throws SQLException  {
        String url="jdbc:mysql://localhost:3306/esfotventas";
        String user="root";
        String password="12345";
        return DriverManager.getConnection(url,user,password);
    }

    public void verdatos()throws SQLException{
        //otra manera
        String nombre =nom.getText();
        //connection puede variar para las diferentes funciones
        Connection conectado = conexion();
        String sql="select * from estudiantes where edad=?";
        PreparedStatement pstmt = conectado.prepareStatement(sql);
        pstmt.setString(1,nombre);
        ResultSet RS = pstmt.executeQuery();
        if (RS.next()){
            String Edad = RS.getString("edad");
            JOptionPane.showMessageDialog(null, " nombre: "+nombre+" edad:"+Edad);

        }


        pstmt.close();
        conectado.close();




        Connection connection=conexion();
        String query="select * from estudiantes";
        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery(query);
        while(resultSet.next()){
            System.out.println(query);
            System.out.println(resultSet.getString("nombre"));
        }
    }






}
