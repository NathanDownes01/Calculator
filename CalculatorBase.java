package com.calc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class CalculatorBase extends JFrame implements KeyListener {
    JButton[] numbers;
    JButton decimal;
    JButton plus,minus,div,mult;
    JButton enter;
    JLabel screen;
    GridBagConstraints c;
    String[] input;
    String lastInput;
    String currentText;
    int left,right;
    LinkedList<Integer> indexes;
    final int HEIGHT = 600;
    final int WIDTH = 300;

    public CalculatorBase(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        c = new GridBagConstraints();
        currentText="";
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
        //screen
        screen = new JLabel("0");
        c.gridx=0;
        c.gridy=0;
        c.gridwidth=4;
        c.gridheight=1;
        c.fill=GridBagConstraints.HORIZONTAL;
        add(screen,c);
        //numbers and decimal
        numberButtons();
        //operators
        operatorButtons();
        //enter
        enter = new JButton("ENTER");
        enter.setName("Enter");
        enter.addActionListener(logic);
        c.gridx=0;
        c.gridy=6;
        c.gridwidth=4;
        c.gridheight=1;
        c.fill=GridBagConstraints.HORIZONTAL;
        add(enter,c);
        pack();
        setVisible(true);
    }

    public void numberButtons(){
        numbers = new JButton[10];
        //adds numbers 9-1
        for(int num=9; num>0; num--){
            String text = ""+num;
            numbers[num] = new JButton(text);
            numbers[num].setName(text);
            numbers[num].addActionListener(clicked);
            c.gridx=(num+2)%3;//x cord
            //y cord
            if(num<10&&num>6){
                c.gridy=2; //top row
            }else if(num<7&&num>3){
                c.gridy=3; //mid row

            }else{
                c.gridy=4; //bot row
            }
            c.gridwidth=1;
            c.gridheight=1;
            c.ipadx=20;
            c.ipady=20;
            c.fill = GridBagConstraints.HORIZONTAL;

            add(numbers[num],c);
        }
        //decimal
        decimal = new JButton(".");
        decimal.setName(".");
        decimal.addActionListener(clicked);
        c.gridx = 2;
        c.gridy=5;
        c.gridwidth=1;
        c.gridheight=1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(decimal,c);
        //0
        numbers[0] = new JButton("0");
        numbers[0].setName("0");
        numbers[0].addActionListener(clicked);
        c.gridx=0;
        c.gridy=5;
        c.gridwidth=2;
        c.gridheight=1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(numbers[0],c);
    }

    public void operatorButtons(){
        //plus
        plus = new JButton("+");
        plus.setName(" + ");
        plus.addActionListener(clicked);
        c.gridx = 3;
        c.gridy=2;
        c.gridwidth=1;
        c.gridheight=1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(plus,c);
        //minus
        minus = new JButton("-");
        minus.setName(" - ");
        minus.addActionListener(clicked);
        c.gridx = 3;
        c.gridy=3;
        c.gridwidth=1;
        c.gridheight=1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(minus,c);
        //div
        div = new JButton("/");
        div.setName(" / ");
        div.addActionListener(clicked);
        c.gridx = 3;
        c.gridy=4;
        c.gridwidth=1;
        c.gridheight=1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(div,c);
        //mult
        mult = new JButton("*");
        mult.setName(" * ");
        mult.addActionListener(clicked);
        c.gridx = 3;
        c.gridy=5;
        c.gridwidth=1;
        c.gridheight=1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(mult,c);

    }

    public ActionListener clicked = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           JButton button = (JButton)e.getSource();
           //first input cant be operator
            if(currentText==""&&(button.getName()==" * "||button.getName()==" + "||
                    button.getName()==" - "||button.getName()==" / ")){
                return;
            }

           //change operator
            if((lastInput==" * "||lastInput==" + "||lastInput==" - "||lastInput==" / ")
                    &&(button.getName()==" * "||button.getName()==" + "||button.getName()==" - "||button.getName()==" / ")){
                currentText=currentText.substring(0,currentText.length()-3);
            }
            lastInput = button.getName();
            currentText += lastInput;
            screen.setText(currentText);
            requestFocus();
        }
    };


    public void back(){
        if(lastInput.equals(" * ")||lastInput.equals(" + ")||lastInput.equals(" - ")||lastInput.equals(" / ")){
            currentText=currentText.substring(0,currentText.length()-3);
        }else{
            currentText=currentText.substring(0,currentText.length()-1);
        }
        screen.setText(currentText);
        lastInput=currentText.substring(currentText.length()-1);
        //if one before was an operator
        if(lastInput.equals(" ")){

            lastInput=currentText.substring(currentText.length()-3);
        }


    }

    public ActionListener logic = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            input = currentText.split(" ");
            //bidmas
            indexes = new LinkedList<>();
            //mult
            for(int i =0;i<input.length-1;i++){
                if(input[i].equals("*")){
                    indexes.add(i);
                }
            }
            //div
            for(int i =0;i<input.length-1;i++){
                if(input[i].equals("/")){
                    indexes.add(i);
                }
            }
            //plus
            for(int i =0;i<input.length-1;i++){
                if(input[i].equals("+")){
                    indexes.add(i);
                }
            }
            //sub
            for(int i =0;i<input.length-1;i++){
                if(input[i].equals("-")){
                    indexes.add(i);
                }
            }
            //calculation
            System.out.println(indexes);
            for(int i=0;i<indexes.size();i++){
                //operands
                left=indexes.get(i)-1;
                right=indexes.get(i)+1;
                while(input[left].equals("BLANK")){
                    left--;
                }
                while(input[right].equals("BLANK")){
                    right++;
                }
                //opcode
                switch (input[indexes.get(i)]){
                    case "*":
                        input[indexes.get(i)]=Float.toString(Float.parseFloat(input[left])*Float.parseFloat(input[right]));
                        break;
                    case "/":
                        input[indexes.get(i)]=Float.toString(Float.parseFloat(input[left])/Float.parseFloat(input[right]));
                        break;
                    case "+":
                        input[indexes.get(i)]=Float.toString(Float.parseFloat(input[left])+Float.parseFloat(input[right]));
                        break;
                    case "-":
                        input[indexes.get(i)]=Float.toString(Float.parseFloat(input[left])-Float.parseFloat(input[right]));
                        break;
                }
                input[left]="BLANK";
                input[right]="BLANK";
                currentText = input[indexes.get(i)];
            }
            screen.setText(currentText);
            requestFocus();

        }
    };



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CalculatorBase calc = new CalculatorBase();
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //used for typing
    @Override
    public void keyPressed(KeyEvent e) {
        for(int i =0; i<10;i++){
            if(numbers[i].getName().equals(Character.toString(e.getKeyChar()))){
                numbers[i].doClick();
            }
        }
        switch (Character.toString(e.getKeyChar())){
            case "/":
                div.doClick();
                break;
            case "*":
                mult.doClick();
                break;
            case "+":
                plus.doClick();
                break;
            case "-":
                minus.doClick();
                break;
            case ".":
                decimal.doClick();
                break;
        }
        if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
            back();
        }
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            enter.doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
