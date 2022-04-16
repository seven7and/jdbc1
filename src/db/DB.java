package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe com métodos estáticos para conexão com o Banco de Dados
 */
public class DB {
    /**
     * Objeto de conexão com o banco de dados do JDBC
     */
    private static Connection conn = null;

    /**
     * Método para conexão com o banco de dados
     */
    public static Connection getConnection() {
        //Verifica se a conexão ainda está nula, se estiver ele faz a conexão
        if (conn == null) {
            try {
                //Instaciado um objeto Properties onde ele recebe o método de carregar as propriedades do db.properties
                Properties props = loadProperties();
                //Variável URL recebendo a propriedade dburl do db.properties
                String url = props.getProperty("dburl");
                //Variável conn fazendo a conexão, pegando a url e as propriedades
                conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }

    /**
     * Método responsável por fechar a conexão com o BDD
     */
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    /**
     * Método auxiliar, responsável por retornar o conteúdo do arquivo db.properties
     */
    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }

    }
}
