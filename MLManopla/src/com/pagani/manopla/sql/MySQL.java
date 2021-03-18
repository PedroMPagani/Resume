package com.pagani.manopla.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MySQL implements AtlasSQLConnection{

	public static final ConsoleCommandSender console = Bukkit.getConsoleSender();

	private String host;
	private String user;
	private String password;
	private String database;
	private ArrayList<String> tablesNames = new ArrayList<String>();
	private int port;

	private Connection connection;
	private JavaPlugin plugin;

	public MySQL(JavaPlugin plugin, String host, String user, String password, String database, int port) {
		super();
		this.plugin = plugin;
		this.host = host;
		this.user = user;
		this.password = password;
		this.database = database;
		this.port = port;
	}

	@Override
	public Connection getConnection() {
		return connection;
	}

	public void runUpdate(AtlasSQLUpdateRunnable sql, boolean asynchronously) {
		if (asynchronously) {
			new BukkitRunnable() {

				@Override
				public void run() {
					sql.run();
				}
			}.runTaskAsynchronously(JavaPlugin.getPlugin(plugin.getClass()));
		}else {
			sql.run();
		}
	}

	public void runSQL(AtlasSQLRunnable sql, boolean asynchronously) {
		if (asynchronously) {
			new BukkitRunnable() {

				@Override
				public void run() {
					sql.run();
				}
			}.runTaskAsynchronously(JavaPlugin.getPlugin(plugin.getClass()));
		} else {
			sql.run();
		}
	}

	public void addTableStatement(String tableName) {
		this.tablesNames.add(tableName);
	}

	@Override
	public void openConnection() {
		if (connection != null) {
			throw new AtlasSQLException("§7[ML] Não foi possível inicializar o MySQL pois esta conexão atual já está aberta.");
		}
		try {
			this.connection = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database+"?autoReconnect=true", user, password);
			Bukkit.getConsoleSender().sendMessage("§7[ML] A conexão com o MySQL foi efetuada com sucesso, criando tabelas...");
			createTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void closeConnection() {
		if(connection == null) {
			throw new AtlasSQLException("[ML] A conexão remota com o servidor MySQL já foi encerrada.");
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createTable() {
		if(connection!=null) {
			if(this.tablesNames.size() > 0) {
				for(String tableStatement : this.tablesNames) {
					try(Statement stmt = connection.createStatement()){
						stmt.executeUpdate(tableStatement);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}else {
				throw new AtlasSQLException("[ML] Nenhuma tabela foi adicionada para ser carregada.");
			}
		}else {
			throw new AtlasSQLException("[ML] A conexão com o servidor MySQL ainda não foi aberta por este motivo a tabela não foi criada.");
		}
	}
}
