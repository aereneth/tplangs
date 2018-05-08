import java.util.*;
import javax.swing.*;
        
public class Compiler extends Thread {
    JTextArea compiler, console;
    
    HashMap<String, Integer> variable = new HashMap<>();
    ArrayList<String> line = new ArrayList<>();
    
    boolean inputMode = false;
    boolean commentMode = false;
    boolean error = false;
    
    public Compiler(JTextArea compilerArea, JTextArea consoleArea){
        this.compiler = compilerArea;
        this.console = consoleArea;
    }
    
    public void compile(){
        String code = compiler.getText();
        code = code.replace("*/","*/;");
        for(String l : code.split(";")){
            if(l.isEmpty() || l.equals("\n"))
                continue;
            line.add(l.trim());
            analyze(l.trim());
            if(error)
                break;
        }
        //console.append("\nEND-OF-CODE");
    }
    
    public void analyze(String sentence){
        String input = "";
        
        if(sentence.contains("/*"))
            commentMode = true;
        
        if(!commentMode){
            switch(sentence.split(" ")[0].toLowerCase()){
                case "var":
                    sentence = sentence.replace("var", "").replace(" ", "").replace("-", "+-");
                    if(!variable.containsKey(sentence.split("=")[0]) && sentence.split("=")[0].matches("^[A-Za-z]{1}[_0-9A-Za-z]*$"))
                        if(sentence.split("=").length <= 1)
                            variable.put(sentence.split("=")[0], 0);
                        else
                            variable.put(sentence.split("=")[0], var(sentence.split("=")[1]));
                    else{
                        JOptionPane.showMessageDialog(compiler, "Invalid Identifier!", "Error", JOptionPane.ERROR_MESSAGE);
                        error = true;
                    }
                    break;

                case "input":
                    sentence = sentence.replace("input", "").trim();
                    input = JOptionPane.showInputDialog(console, "Input", "Enter value of " + sentence, JOptionPane.QUESTION_MESSAGE);

                    if(!variable.containsKey(sentence))
                        variable.put(sentence, var(input));
                    else
                        variable.replace(sentence, var(input));

                    console.append(input + "\n");
                    break;

                case "output":
                    sentence = sentence.replace("output", "").trim();
                    console.append(output(sentence));
                    break;

                default:
                    sentence = sentence.replace(" ", "").replace("-", "+-").trim();
                    if(variable.containsKey(sentence.split("=")[0]))
                        variable.replace(sentence.split("=")[0], var(sentence.split("=")[1]));
                    else{
                        JOptionPane.showMessageDialog(compiler, "Variable not found!", "Error", JOptionPane.ERROR_MESSAGE);
                        error = true;
                    }
            }
        }
        
        if(sentence.contains("*/"))
            commentMode = false;
    }
    
    public int var(String equation){
        ArrayList<Integer> val = new ArrayList<>();
        ArrayList<String> opr = new ArrayList<>();
        
        String newEquation = "";
        int degree = 0;
        int ctr = 0;
        int size = 0;
        int startIndex = 0;
        int endIndex = 0;
        
        for(char x : equation.toCharArray()){
            switch(x){

                case '(':
                    if(++degree == 1)
                        startIndex = ctr;
                    break;

                case ')':
                    if(--degree == 0)
                        endIndex = ctr;
                    break;

                default:
                    if(degree > 0)
                        newEquation += x;
            }

            if(degree == 0 && !newEquation.isEmpty()){
                equation = equation.substring(0, startIndex) + var(newEquation) + equation.substring(endIndex+1, equation.length());
                ctr = startIndex + String.valueOf(var(newEquation)).length() - 1;
                newEquation = "";
            }
            ++ctr;
        }
        
        for(String x : equation.replace(" ", "").split("[\\.\\-0-9A-Za-z]"))
            if(!x.isEmpty())
                opr.add(x.trim());
        
        for(String x : equation.split("[\\+\\*\\/]")){
            if(x.trim().matches("^[\\.\\-0-9]+$"))
                val.add(Integer.parseInt(x));    
            else
                val.add(variable.get(x));
        }
        
        ctr = 0;
        size = opr.size();
        
        while(ctr <= size-1 && ctr >= 0){
            if(opr.get(ctr).equals("*")){
                val.set(ctr, val.get(ctr)*val.get(ctr+1));
                opr.remove(ctr);
                val.remove(ctr+1);
                --ctr;
                size = opr.size();
                continue;
            }
            
            if(opr.get(ctr).equals("/")){
                val.set(ctr, val.get(ctr)/val.get(ctr+1));
                opr.remove(ctr);
                val.remove(ctr+1);
                size = opr.size();
                --ctr;
                continue;
            }
            
            ++ctr;
        }
        
        ctr = 0;
        size = opr.size();
        
        while(ctr <= size-1 && ctr >= 0){
            if(opr.get(ctr).equals("+")){
                val.set(ctr, val.get(ctr)+val.get(ctr+1));
                opr.remove(ctr);
                val.remove(ctr+1);
                size = opr.size();
                --ctr;
                continue;
            }
            
            ++ctr;
        }
        
        return val.get(0);
    }
    
    public String output(String output){
        String result = "";
        String buffer = "";
        boolean isString = false;

        for(char x : output.toCharArray()){
            if(x=='\"'){
                isString = !isString;
                if (!buffer.trim().isEmpty()) {
                    result += !buffer.trim().isEmpty() ? variable.get(buffer.trim()) : "";
                    buffer = "";
                } 
                continue;
            }
            
            if(x=='.')
                continue;
            
            if(isString)
                result += x;
            else
                buffer += x;
        }
        
        if (!buffer.trim().isEmpty()) {
            result += !buffer.trim().isEmpty() ? variable.get(buffer.trim()) : "";
            buffer = "";
        }
        return result;
    }
}
