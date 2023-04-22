package com.mycompany.bce160_lru_cache_implementation;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.awt.*;
class Object{
    String name,rollno;
    String sem;
    Object(){
        this.name = "";
        this.rollno = "";
        this.sem = "";
    }
    Object(String n, String rn, String s){
        this.name =n;
        this.rollno = rn;
        this.sem = s;
    }
}
class Node{
    String rollno;
    Node left;
    Object obj;
    Node right;
    Node(){
        this.rollno = "";
        this.left = null;
        this.right = null;
        obj = new Object();
    }
    Node(String r, Node left, Node right, String n, String sem){
        this.rollno = r;
        this.left = left;
        this.right = right;
        obj.name = n;
        obj.rollno = r;
        obj.sem = sem;
    }
    Node(String r){
        this.rollno = r;
        this.left = null;
        this.obj = null;
        this.right = null;
    }
    Node addObject(Node n, String name, String rollno, String sem){
        n.obj = new Object(name, rollno, sem);
        return n;
    }
    void print_data(DefaultTableModel model){
        model.addRow(new String[]{this.obj.rollno, this.obj.name, this.obj.sem});
//        System.out.println("Name: "+this.obj.name);
//        System.out.println("Roll No: "+this.obj.rollno);
//        System.out.println("Semester: "+this.obj.sem);
//        System.out.println("=======================================================");
    }
    void setLeftChild(Node node){
        this.left = node;
    }
    void setRightChild(Node node){
        this.right = node;
    }
    Node getLeftChild(){
        return this.left;
    }
    Node getRightChild(){
        return this.right;
    }
    String getValue(){
        return this.rollno;
    }
}
public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
           public void run(){
               new implement();
           } 
        });
    }
}
class implement implements ActionListener{
    JFrame f;
    boolean status = true;
    JButton insert, delete, view, search, update, clear;
    JLabel name, roll, sem, roll_del, roll_s,name_u, roll_u, sem_u, lbl_i, lbl_del, lbl_vi, lbl_s, lbl_up;
    JPanel panel1,panel2,panel3,panel4,panel5;
    JTextField nme, rol, seme, roll_d, rol_s, rol_u,sm_u, nme_u;
    JTabbedPane tb;
    JScrollPane scrl, scrl_f;
    JTable table, table_f;
    int treeHeight;
    int nodeRadius = 27;
    int horizontalGap = 40;
    int verticalGap = 60;
    int startingX;
    int startingY = 20;
    DefaultTableModel model, model_f;
    Node root;
    String find_r = "",find_n="",find_s ="";
    implement(){
        root = null;
        lbl_i = new JLabel();
        lbl_del = new JLabel();
        lbl_vi = new JLabel();
        lbl_s = new JLabel();
        lbl_up = new JLabel();
        f = new JFrame("LRU Cache using Splay Trees");
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new FlowLayout());
        f.setSize(500,500);
        insert = new JButton("Insert");
        name = new JLabel("Name: ");
        roll = new JLabel("Roll No: ");
        sem = new JLabel("Semester: ");
        nme = new JTextField(10);
        rol = new JTextField(10);
        seme = new JTextField(10);
        clear = new JButton("Clear Data!");
        tb = new JTabbedPane();
        panel1 = new JPanel();
        panel1.add(name);
        panel1.add(nme);
        panel1.add(roll);
        panel1.add(rol);
        panel1.add(sem);
        panel1.add(seme);
        panel1.add(insert);
        panel1.add(lbl_i);
        tb.add("Insert",panel1);
        panel2 = new JPanel();
        delete = new JButton("Delete");
        roll_del = new JLabel("Roll No: ");
        roll_d = new JTextField(10);
        panel2.add(roll_del);
        panel2.add(roll_d);
        panel2.add(delete);
        panel2.add(lbl_del);
        tb.add("Delete",panel2);
        panel3 = new JPanel();
        String columns[] = {"Roll No","Name", "Semester"};
        model = new DefaultTableModel(columns,0);
        table = new JTable(model);
        scrl = new JScrollPane(table);
        scrl.setPreferredSize(new Dimension(500,500));
        panel3.add(scrl);
        view = new JButton("Refresh");
        panel3.add(view);
        panel3.add(lbl_vi);
        tb.add("View",panel3);
        panel4 = new JPanel();
        roll_s = new JLabel("Roll No: ");
        rol_s = new JTextField(10);
        search = new JButton("Search");
        panel4.add(roll_s);
        panel4.add(rol_s);
        panel4.add(search);
        panel4.add(lbl_s);
        String cols[] = {"Roll No","Name","Semester","Time in ns"};
        model_f = new DefaultTableModel(cols,0);
        table_f = new JTable(model_f);
        scrl_f = new JScrollPane(table_f);
        scrl_f.setPreferredSize(new Dimension(500,500));
        panel4.add(scrl_f);
        tb.add("Search",panel4);
        panel5 = new JPanel();
        roll_u = new JLabel("Roll No: ");
        rol_u = new JTextField(10);
        name_u = new JLabel("Name: ");
        nme_u = new JTextField(10);
        sem_u = new JLabel("Semester: ");
        sm_u = new JTextField(10);
        panel5.add(roll_u);
        panel5.add(rol_u);
        panel5.add(name_u);
        panel5.add(nme_u);
        panel5.add(sem_u);
        panel5.add(sm_u);
        update = new JButton("Update");
        panel5.add(update);
        panel5.add(lbl_up);
        tb.add("Update",panel5);
        f.add(tb);f.add(clear);
        f.pack();
        update.addActionListener(this);
        search.addActionListener(this);
        view.addActionListener(this);
        insert.addActionListener(this);
        delete.addActionListener(this);
        clear.addActionListener(this);
        f.getContentPane().setBackground(Color.CYAN);
        panel1.setBackground(Color.yellow);
        panel2.setBackground(Color.GREEN);
        panel3.setBackground(Color.WHITE);
        panel4.setBackground(Color.MAGENTA);
        panel5.setBackground(Color.ORANGE);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==insert){
            if(!(nme.getText().equals("")) && !(rol.getText().equals("")) && !(seme.getText().equals(""))){
                String r = rol.getText();
                String n = nme.getText();
                String s = seme.getText();
                root = insert_node(n, r, s, root);
                if(status){
                    lbl_i.setText("Entry named "+n+" inserted successfully");
                }
                else{
                    lbl_i.setText("Data is already present! If you want to update it, go to update!");
                    status = true;
                }
                rol.setText("");
                nme.setText("");
                seme.setText("");
            }
            else{
                lbl_i.setText("Fill all the fields");
            }
            f.pack();
        }
        else if(e.getSource() == delete){
            if(!roll_d.getText().equals("")){
                String r = roll_d.getText();
                if(root==null){
                    lbl_del.setText("Database is Empty!");
                }
                else{
                    root = delete_node(root, r);
                    if(status){
                        lbl_del.setText("Entry of "+r+" deleted successfully");
                        
                    }
                    else{
                        lbl_del.setText("Data doesn't exists!");
                        status = true;
                    }
                }
                roll_d.setText("");
            }
            else{
                lbl_del.setText("Enter Roll No!");
            }
            f.pack();
        }
        else if(e.getSource() == view){
            int row = table.getRowCount();
            for(int i=row-1;i>=0;i--){
                model.removeRow(i);
            }
            print_tree(root);
            f.pack();
        }
        else if(e.getSource() == search){
            if(!rol_s.getText().equals("")){
                String r = rol_s.getText();
                if(root==null){
                    lbl_s.setText("Database is empty!");
                }
                else{
                    long start = System.nanoTime();
                    root = find_data(root, r);
                    long end = System.nanoTime();
                    if(status){
                        model_f.addRow(new String[]{find_r,find_n,find_s, String.valueOf(end-start)});
//                        lbl_s.setText("Time taken: "+String.valueOf(end-start));
                        lbl_s.setText("");
                    }
                    else{
                        lbl_s.setText("Data doesn't Exists\n Time taken: "+String.valueOf(end-start));
                        status = true;
                    }
                }
            }
            f.pack();
        }
        else if(e.getSource()==update){
             if(!(nme_u.getText().equals("") && rol_u.getText().equals("") && sm_u.getText().equals(""))){
                String r = rol_u.getText();
                String n = nme_u.getText();
                String s = sm_u.getText();
                long start = System.nanoTime();
                root = update_data(root, r, n, s);
                long end = System.nanoTime();
                if(status){
                    lbl_up.setText("Data Updated successfully. Time Taken: "+String.valueOf(end-start));
                }
                else{
                    lbl_up.setText("Data doesn't exists");
                    status = true;
                }
                rol_u.setText("");
                nme_u.setText("");
                sm_u.setText("");
            }
            else{
                lbl_i.setText("Fill all the fields");
            }
            f.pack();
        }
        else if(e.getSource()==clear){
            root=null;
            lbl_i.setText("");
            lbl_s.setText("");
            lbl_up.setText("");
            lbl_vi.setText("");
            lbl_del.setText("");
            nme.setText("");
            rol.setText("");
            seme.setText("");
            roll_d.setText("");
            rol_s.setText("");
            rol_u.setText("");
            sm_u.setText("");
            nme_u.setText("");
            int row_count = table_f.getRowCount();
            for(int i=row_count-1;i>=0;i--){
                model_f.removeRow(i);
            }
            status = true;
        }
    }
    public Node delete_node(Node root, String rollno){
        Node temp = root, prev = root;
        while(temp!=null){
            if(temp.rollno.compareToIgnoreCase(rollno)<0){
                prev = temp;
                temp = temp.right;
            }
            else if(temp.rollno.compareToIgnoreCase(rollno)>0){
                prev = temp;
                temp = temp.left;
            }
            else{
                //Logic to delete
                if(temp==root){
                    if(temp.right==null){
                        root = root.left;
                    }
                    else if(temp.left == null){
                        root = root.right;
                    }
                    else{
                        Node tempp = temp.right;
                        while(tempp.left!=null){
                            prev  = tempp;
                            tempp = tempp.left;
                        }
                        temp.rollno = tempp.rollno;
                        temp.obj.name = tempp.obj.name;
                        temp.obj.sem = tempp.obj.sem;
                        temp.obj.rollno = tempp.obj.rollno;
                        if(tempp.right==null){
                            if(prev.left == tempp){
                                prev.left = tempp.left;
                            }
                            else{
                                prev.right = tempp.left;
                            }
                        }
                        else if(tempp.left == null){
                            if(prev.left == tempp){
                                prev.left = tempp.right;
                            }
                            else{
                                prev.right = tempp.right;
                            }
                        }
                    }
                }
                else{
                    if(temp.left == null){
                        if(prev.left==temp){
                            prev.left = temp.right;
                        }
                        else{
                            prev.right = temp.right;
                        }
                    }
                    else if(temp.right==null){
                        if(prev.left==temp){
                            prev.left = temp.left;
                        }
                        else{
                            prev.right = temp.left;
                        }
                    }
                    else{
                        Node tempp = temp.right;
                        prev = temp;
                        while(tempp.left!=null){
                            prev  = tempp;
                            tempp = tempp.left;
                        }
                        temp.rollno = tempp.rollno;
                        temp.obj.name = tempp.obj.name;
                        temp.obj.sem = tempp.obj.sem;
                        temp.obj.rollno = tempp.obj.rollno;
                        // Delete the inorder successor which will be leftmost element in right subtree.
                        if(tempp.right==null){
                            if(prev.left == tempp){
                                prev.left = tempp.left;
                            }
                            else{
                                prev.right = tempp.left;
                            }
                        }
                        else if(tempp.left == null){
                            if(prev.left == tempp){
                                prev.left = tempp.right;
                            }
                            else{
                                prev.right = tempp.right;
                            }
                        }
                    }
                }
                return root;
            }
        }
        status = false;
        return root;
    }
    public void print_tree(Node root){
        if(root == null){
            return;
        }
        print_tree(root.left);
        root.print_data(model);
        print_tree(root.right);
    }
    public Node insert_node(String name, String rollno, String sem, Node root){
        if(root == null){
            root = new Node(rollno);
            root.addObject(root, name, rollno, sem);
            return root;
        }
        else{
            Node temp = root, prev = root;
            while(temp!=null){
                prev = temp;
                if(temp.rollno.compareToIgnoreCase(rollno)<0){
                    temp = temp.right;
                }
                else if(temp.rollno.compareToIgnoreCase(rollno)>0){
                    temp = temp.left;
                }
                else{
                    System.out.println("Duplicated Data!");
                    status = false;
                    return root;
                }
            }
            if(prev.rollno.compareTo(rollno)<0){
                Node newnode = new Node(rollno);
                newnode.addObject(newnode, name, rollno, sem);
                prev.right = newnode;
            }
            else{
                Node newnode = new Node(rollno);
                newnode.addObject(newnode, name, rollno, sem);
                prev.left = newnode;
            }
            return root;
        }
    }
    public Node relocate_keys(Stack<Node> s, Node root){
        Node current = s.pop();
        // System.out.println("Current: "+current.rollno);
        while(current!=root){
            Node upper = s.pop();
            // System.out.println("Upper: "+upper.rollno);
            if(upper==root){
                if(upper.left == current){
                    right_rotation(upper);
                }   
                else{
                    left_rotation(upper);
                }
                current = upper;
            }
            else{
                Node upper_parent = s.pop();
                // System.out.println("Upper Parent: "+upper_parent.rollno);
                if(upper == upper_parent.left){
                    if(current == upper.left){
                        right_rotation(upper_parent);
                    }
                    else{
                        left_rotation(upper);
                    }
                    right_rotation(upper_parent);
                }
                else{
                    if(current == upper.left){
                        right_rotation(upper);
                    }
                    else{
                        left_rotation(upper_parent);
                    }
                    left_rotation(upper_parent);
                }
                current = upper_parent;
            }
        }
        return root;
    }
    public void left_rotation(Node n){
        Node temp = n.left;
        String roll = n.rollno;
        Object ob = n.obj;
        n.left = n.right;
        n.rollno = n.right.rollno;
        n.obj = n.right.obj;
        n.right = n.left.right;
        n.left.right = n.left.left;
        n.left.left = temp;
        n.left.rollno = roll;
        n.left.obj = ob;
        // System.out.println("Root: "+n.rollno);
        // if(n.left!=null)
        // System.out.println("Left: "+n.left.rollno);
        // print_tree(n);
        // if(n.right!=null)
        // System.out.println("Right: "+n.right.rollno);
    }
    public void right_rotation(Node n){
        Node temp = n.right;
        String roll = n.rollno;
        Object ob = n.obj;
        n.right = n.left;
        n.rollno = n.left.rollno;
        n.obj = n.left.obj;
        n.left = n.right.left;
        n.right.left = n.right.right;
        n.right.right = temp;
        n.right.rollno = roll;
        n.right.obj = ob;
    }
    public Node update_data(Node root, String rollno, String name, String sem){
        Node temp = root;
        Stack<Node> s = new Stack<>();
        s.push(temp);
        while(temp!=null){
            if(temp.rollno.compareToIgnoreCase(rollno)<0){
                temp = temp.right;
                s.push(temp);
            }
            else if(temp.rollno.compareToIgnoreCase(rollno)>0){
                temp = temp.left;
                s.push(temp);
            }
            else{
                // s.push(temp);
                temp.rollno = rollno;
                temp.obj.rollno = rollno;
                temp.obj.sem = sem;
                temp.obj.name = name;
                root = relocate_keys(s, root);
                System.out.println("Data Updated Successfully");
                status = true;
                return root;
            }
        }
        status = false;
        return root;
    }
    public Node find_data(Node root, String rollno){
        Node temp = root;
        Stack<Node> s = new Stack<>();
        s.push(temp);
        while(temp!=null){
            if(temp.rollno.compareToIgnoreCase(rollno)<0){
                temp = temp.right;
                s.push(temp);
            }
            else if(temp.rollno.compareToIgnoreCase(rollno)>0){
                temp = temp.left;
                s.push(temp);
            }
            else{
                find_r = temp.obj.rollno;
                find_n = temp.obj.name;
                find_s = temp.obj.sem;
                // System.out.println("Stack"+s);
                root = relocate_keys(s, root);
                return root;
            }
        }
        status = false;
        return root;
    }
}