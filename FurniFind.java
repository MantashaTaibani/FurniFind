import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class FurniFind extends Frame {
    static Connection con;
    static Frame loginFrame;
    static TextField username,password;

    Color buttonColor=new Color(45,85,135);
    TextArea area;

    public FurniFind() {
        setTitle("Luxur&Lavish - Catalogue System");
        setSize(1100,650);
        setLayout(new BorderLayout(8,8));

        Label heading=new Label("Luxur&Lavish",Label.CENTER);
        heading.setFont(new Font("Serif",Font.BOLD,28));
        add(heading,BorderLayout.NORTH);

        area=new TextArea("",15,100,TextArea.SCROLLBARS_BOTH);
        area.setEditable(false);
        area.setFont(new Font("Monospaced",Font.PLAIN,12));
        add(area,BorderLayout.CENTER);

        Panel bottom=new Panel(new BorderLayout(5,8));

        Panel buttons=new Panel(new GridLayout(0,3,8,8));

        Button search=new Button("Search Catalogue");
        Button budget=new Button("Budget Filter");
        Button availability=new Button("Availability Filter");
        Button view=new Button("View All Catalogues");
        Button add=new Button("Add Catalogue");
        Button update=new Button("Update Catalogue");
        Button delete=new Button("Delete Catalogue");
        Button borrow=new Button("Borrow Catalogue");
        Button returnButton=new Button("Return Catalogue");
        Button records=new Button("Borrowing Records");

        Button all[]={search,budget,availability,view,add,update,
                      delete,borrow,returnButton,records};

        for(int i=0;i<all.length;i++) {
            all[i].setBackground(buttonColor);
            all[i].setForeground(Color.WHITE);
            all[i].setFont(new Font("SansSerif",Font.BOLD,13));
            buttons.add(all[i]);
        }

        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchCatalogue();
            }
        });

        budget.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                budgetFilter();
            }
        });

        availability.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                availabilityFilter();
            }
        });

        view.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewAll();
            }
        });

        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addCatalogue();
            }
        });

        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCatalogue();
            }
        });

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteCatalogue();
            }
        });

        borrow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                borrowCatalogue();
            }
        });

        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnCatalogue();
            }
        });

        records.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                borrowingRecords();
            }
        });

        bottom.add(buttons,BorderLayout.CENTER);

        Panel logoutPanel=new Panel(new FlowLayout(FlowLayout.CENTER));
        Button logout=new Button("Logout");
        styleButton(logout);

        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                login();
            }
        });

        logoutPanel.add(logout);
        bottom.add(logoutPanel,BorderLayout.SOUTH);
        add(bottom,BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeConnection();
                dispose();
                System.exit(0);
            }
        });

        viewAll();
    }

    void styleButton(Button b) {
        b.setBackground(buttonColor);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif",Font.BOLD,13));
    }

    static void connect() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con=DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/furnifind","root",""
        );
    }

    static void login() {
        loginFrame=new Frame("Luxur&Lavish Login");
        loginFrame.setSize(350,220);
        loginFrame.setLayout(new BorderLayout(8,8));

        Label heading=new Label("Luxur&Lavish",Label.CENTER);
        heading.setFont(new Font("Serif",Font.BOLD,24));
        loginFrame.add(heading,BorderLayout.NORTH);

        Panel fields=new Panel(new GridLayout(2,2,8,8));

        username=new TextField(15);
        password=new TextField(15);
        password.setEchoChar('*');

        fields.add(new Label("Username:"));
        fields.add(username);
        fields.add(new Label("Password:"));
        fields.add(password);
        loginFrame.add(fields,BorderLayout.CENTER);

        Panel buttons=new Panel(new FlowLayout());

        Button loginButton=new Button("Login");
        Button cancelButton=new Button("Cancel");

        loginButton.setBackground(new Color(45,85,135));
        loginButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(45,85,135));
        cancelButton.setForeground(Color.WHITE);

        buttons.add(loginButton);
        buttons.add(cancelButton);
        loginFrame.add(buttons,BorderLayout.SOUTH);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(username.getText().equals("admin")&&
                   password.getText().equals("1234")) {
                    loginFrame.dispose();
                    new FurniFind().setVisible(true);
                } else {
                    username.setText("");
                    password.setText("");
                    loginFrame.setTitle("Invalid Login - Try Again");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();
                closeConnection();
                System.exit(0);
            }
        });

        loginFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeConnection();
                loginFrame.dispose();
                System.exit(0);
            }
        });

        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    static void closeConnection() {
        try {
            if(con!=null&&!con.isClosed()) {
                con.close();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    String ask(String question) {
        Dialog d=new Dialog(this,"Enter Details",true);
        d.setLayout(new BorderLayout(10,10));
        d.setSize(400,160);

        Label label=new Label(question);
        label.setFont(new Font("SansSerif",Font.BOLD,14));
        d.add(label,BorderLayout.NORTH);

        TextField input=new TextField(25);
        Panel inputPanel=new Panel(new FlowLayout());
        inputPanel.add(input);
        d.add(inputPanel,BorderLayout.CENTER);

        Panel buttonPanel=new Panel(new FlowLayout());
        Button ok=new Button("OK");
        Button cancel=new Button("Cancel");

        ok.setBackground(buttonColor);
        ok.setForeground(Color.WHITE);
        cancel.setBackground(buttonColor);
        cancel.setForeground(Color.WHITE);

        buttonPanel.add(ok);
        buttonPanel.add(cancel);
        d.add(buttonPanel,BorderLayout.SOUTH);

        String[] result={""};

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                result[0]=input.getText();
                d.dispose();
            }
        });

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                d.dispose();
            }
        });

        d.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                d.dispose();
            }
        });

        d.setLocationRelativeTo(this);
        d.setVisible(true);
        return result[0];
    }

    String catalogueQuery() {
        return "SELECT c.*,(c.total_stock-" +
            "(SELECT COUNT(*) FROM borrow_records b " +
            "WHERE b.catalogue_id=c.catalogue_id " +
            "AND b.returned_date IS NULL)) AS available_stock " +
            "FROM catalogues c ";
    }

    void loadData(String sql,Object... values) {
        try(PreparedStatement ps=con.prepareStatement(sql)) {
            for(int i=0;i<values.length;i++) {
                ps.setObject(i+1,values[i]);
            }

            try(ResultSet rs=ps.executeQuery()) {
                StringBuilder data=new StringBuilder();

                data.append(String.format(
                    "%-5s %-14s %-18s %-15s %-12s %-7s %-10s %-7s %-7s %-8s %-8s%n",
                    "ID","Company","Catalogue","Design","Category",
                    "SR No","Price","Stock","Avail.","Rack","Stack"
                ));

                data.append(
                    "--------------------------------------------------------------------------------" +
                    "--------------------------------------------------------\n"
                );

                int count=0;

                while(rs.next()) {
                    data.append(String.format(
                        "%-5d %-14s %-18s %-15s %-12s %-7d %-10.2f %-7d %-7d %-8s %-8s%n",
                        rs.getInt("catalogue_id"),
                        rs.getString("company"),
                        rs.getString("catalogue_name"),
                        rs.getString("design"),
                        rs.getString("category"),
                        rs.getInt("sr_no"),
                        rs.getDouble("price"),
                        rs.getInt("total_stock"),
                        rs.getInt("available_stock"),
                        rs.getString("rack_no"),
                        rs.getString("stack_no")
                    ));
                    count++;
                }

                if(count==0) {
                    area.setText("No catalogues found.");
                } else {
                    area.setText(data.toString());
                }
            }
        } catch(Exception e) {
            error(e);
        }
    }

    void viewAll() {
        loadData(catalogueQuery());
    }

    void searchCatalogue() {
        String key=ask("Enter company, catalogue name or design:");

        if(key.trim().isEmpty()) {
            show("Search cancelled or empty.");
            return;
        }

        loadData(catalogueQuery()+
            "WHERE c.company LIKE ? OR c.catalogue_name LIKE ? "+
            "OR c.design LIKE ?",
            "%"+key+"%","%"+key+"%","%"+key+"%");
    }

    void budgetFilter() {
        try {
            double min=Double.parseDouble(ask("Minimum budget:"));
            double max=Double.parseDouble(ask("Maximum budget:"));

            if(min<0||max<min) {
                show("Invalid budget range!");
                return;
            }

            loadData(catalogueQuery()+
                "WHERE c.price BETWEEN ? AND ? "+
                "AND (c.total_stock-(SELECT COUNT(*) "+
                "FROM borrow_records br WHERE br.catalogue_id=c.catalogue_id "+
                "AND br.returned_date IS NULL))>0",
                min,max);
        } catch(Exception e) {
            error(e);
        }
    }

    void availabilityFilter() {
        String choice=ask("Enter 1 for Available or 2 for Fully Borrowed:");

        if(choice.equals("1")) {
            loadData(catalogueQuery()+
                "WHERE (c.total_stock-(SELECT COUNT(*) "+
                "FROM borrow_records b WHERE b.catalogue_id=c.catalogue_id "+
                "AND b.returned_date IS NULL))>0");
        } else if(choice.equals("2")) {
            loadData(catalogueQuery()+
                "WHERE (c.total_stock-(SELECT COUNT(*) "+
                "FROM borrow_records b WHERE b.catalogue_id=c.catalogue_id "+
                "AND b.returned_date IS NULL))=0");
        } else {
            show("Invalid choice!");
        }
    }

    void show(String text) {
        area.setText(text);
    }

    void addCatalogue() {
        try {
            String company=ask("Company:");
            String name=ask("Catalogue Name:");
            String design=ask("Design:");
            String category=ask("Category:");
            int sr=Integer.parseInt(ask("SR No:"));
            double price=Double.parseDouble(ask("Price:"));
            int stock=Integer.parseInt(ask("Total Stock:"));
            String rack=ask("Rack No:");
            String stack=ask("Stack No:");

            if(company.trim().isEmpty()||name.trim().isEmpty()||
               design.trim().isEmpty()||category.trim().isEmpty()||
               sr<0||price<0||stock<1) {
                show("Enter valid catalogue details!");
                return;
            }

            String sql="INSERT INTO catalogues "+
                "(company,catalogue_name,design,category,sr_no,price,"+
                "total_stock,rack_no,stack_no) VALUES (?,?,?,?,?,?,?,?,?)";

            try(PreparedStatement ps=con.prepareStatement(sql)) {
                ps.setString(1,company);
                ps.setString(2,name);
                ps.setString(3,design);
                ps.setString(4,category);
                ps.setInt(5,sr);
                ps.setDouble(6,price);
                ps.setInt(7,stock);
                ps.setString(8,rack);
                ps.setString(9,stack);
                ps.executeUpdate();
            }

            viewAll();
        } catch(Exception e) {
            error(e);
        }
    }

    void updateCatalogue() {
        try {
            int id=Integer.parseInt(ask("Catalogue ID to update:"));
            int borrowed=0;

            try(PreparedStatement ps=con.prepareStatement(
                "SELECT COUNT(*) FROM borrow_records "+
                "WHERE catalogue_id=? AND returned_date IS NULL")) {
                ps.setInt(1,id);

                try(ResultSet rs=ps.executeQuery()) {
                    rs.next();
                    borrowed=rs.getInt(1);
                }
            }

            int stock=Integer.parseInt(ask("New total stock:"));

            if(stock<1||stock<borrowed) {
                show("Stock cannot be less than borrowed copies!");
                return;
            }

            String company=ask("New company:");
            String name=ask("New catalogue name:");
            String design=ask("New design:");
            String category=ask("New category:");
            int sr=Integer.parseInt(ask("New SR No:"));
            double price=Double.parseDouble(ask("New price:"));
            String rack=ask("New rack number:");
            String stack=ask("New stack number:");

            if(sr<0||price<0) {
                show("Invalid values!");
                return;
            }

            String sql="UPDATE catalogues SET company=?,catalogue_name=?,"+
                "design=?,category=?,sr_no=?,price=?,total_stock=?,"+
                "rack_no=?,stack_no=? WHERE catalogue_id=?";

            try(PreparedStatement ps=con.prepareStatement(sql)) {
                ps.setString(1,company);
                ps.setString(2,name);
                ps.setString(3,design);
                ps.setString(4,category);
                ps.setInt(5,sr);
                ps.setDouble(6,price);
                ps.setInt(7,stock);
                ps.setString(8,rack);
                ps.setString(9,stack);
                ps.setInt(10,id);

                if(ps.executeUpdate()>0) {
                    viewAll();
                } else {
                    show("Catalogue ID not found.");
                }
            }
        } catch(Exception e) {
            error(e);
        }
    }

    void deleteCatalogue() {
        try {
            int id=Integer.parseInt(ask("Catalogue ID to delete:"));

            try(PreparedStatement ps=con.prepareStatement(
                "SELECT COUNT(*) FROM borrow_records "+
                "WHERE catalogue_id=? AND returned_date IS NULL")) {
                ps.setInt(1,id);

                try(ResultSet rs=ps.executeQuery()) {
                    rs.next();

                    if(rs.getInt(1)>0) {
                        show("Cannot delete: copies are still borrowed.");
                        return;
                    }
                }
            }

            try(PreparedStatement ps=con.prepareStatement(
                "DELETE FROM catalogues WHERE catalogue_id=?")) {
                ps.setInt(1,id);

                if(ps.executeUpdate()>0) {
                    viewAll();
                } else {
                    show("Catalogue ID not found.");
                }
            }
        } catch(Exception e) {
            error(e);
        }
    }

    void borrowCatalogue() {
        try {
            int id=Integer.parseInt(ask("Catalogue ID to borrow:"));

            String sql="SELECT total_stock-(SELECT COUNT(*) "+
                "FROM borrow_records b WHERE b.catalogue_id=c.catalogue_id "+
                "AND b.returned_date IS NULL) AS available_stock "+
                "FROM catalogues c WHERE c.catalogue_id=?";

            try(PreparedStatement ps=con.prepareStatement(sql)) {
                ps.setInt(1,id);

                try(ResultSet rs=ps.executeQuery()) {
                    if(!rs.next()) {
                        show("Catalogue ID not found.");
                        return;
                    }

                    if(rs.getInt("available_stock")<=0) {
                        show("No copies available!");
                        return;
                    }
                }
            }

            String customer=ask("Customer Name:");

            if(customer.trim().isEmpty()) {
                show("Customer name cannot be empty!");
                return;
            }

            try(PreparedStatement ps=con.prepareStatement(
                "INSERT INTO borrow_records "+
                "(catalogue_id,customer_name,borrowed_date) "+
                "VALUES (?,?,CURDATE())")) {
                ps.setInt(1,id);
                ps.setString(2,customer);
                ps.executeUpdate();
            }

            viewAll();
        } catch(Exception e) {
            error(e);
        }
    }

    void returnCatalogue() {
        borrowingRecords();

        try {
            int id=Integer.parseInt(ask("Borrow ID to return:"));

            try(PreparedStatement ps=con.prepareStatement(
                "UPDATE borrow_records SET returned_date=CURDATE() "+
                "WHERE borrow_id=? AND returned_date IS NULL")) {
                ps.setInt(1,id);

                if(ps.executeUpdate()>0) {
                    viewAll();
                } else {
                    show("Invalid Borrow ID or already returned.");
                }
            }
        } catch(Exception e) {
            error(e);
        }
    }

    void borrowingRecords() {
        try {
            String sql="SELECT b.borrow_id,b.customer_name,"+
                "c.catalogue_name,b.borrowed_date,b.returned_date "+
                "FROM borrow_records b JOIN catalogues c "+
                "ON b.catalogue_id=c.catalogue_id ORDER BY b.borrow_id";

            StringBuilder data=new StringBuilder();

            data.append(String.format(
                "%-10s %-20s %-25s %-15s %-15s%n",
                "Borrow ID","Customer","Catalogue",
                "Borrowed Date","Returned Date"
            ));

            data.append(
                "--------------------------------------------------------------------------------\n"
            );

            try(Statement st=con.createStatement();
                ResultSet rs=st.executeQuery(sql)) {
                while(rs.next()) {
                    data.append(String.format(
                        "%-10d %-20s %-25s %-15s %-15s%n",
                        rs.getInt("borrow_id"),
                        rs.getString("customer_name"),
                        rs.getString("catalogue_name"),
                        rs.getDate("borrowed_date"),
                        rs.getDate("returned_date")==null
                            ?"Not returned"
                            :rs.getDate("returned_date").toString()
                    ));
                }
            }

            area.setText(data.toString());
        } catch(Exception e) {
            error(e);
        }
    }

    void error(Exception e) {
        show("Error: "+e.getMessage());
    }

    public static void main(String[] args) {
        try {
            connect();
            login();
        } catch(Exception e) {
            System.out.println("Database connection failed: "+e.getMessage());
            closeConnection();
        }
    }
}
