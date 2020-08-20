package com.company;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Message message = new Message();
        (new Thread(new Writer(message))).start();
        (new Thread(new Reader(message))).start();
    }
}
class Message{
    private String message;
    private boolean isEmpty = true;

    public synchronized String read()  {
        while (isEmpty){
          try {
              wait();
          }catch ( InterruptedException e ){

          }
        }
        isEmpty = true;
        notifyAll();
        return this.message;

    }
    public synchronized void write(String message) {
        while (!isEmpty){
          try {
              wait();
          }catch ( InterruptedException e ){

          }
        }
        isEmpty = false;
        this.message = message;
        notifyAll();
    }

}

class Writer implements Runnable{
    private Message message;
    public Writer(Message message){
        this.message = message;
    }
    public void run(){
        String [] messages = {"This is pangsui in Java",
                              "He is bent on learning Java",
                              "Keep on Pangsui and you will get there",
                              "This will be your greatest achievement in engineering",
                              "Because programming is cool!"};
        Random random = new Random();
        for (int i=0; i<messages.length;i++){
            message.write(messages[i]);

            try{
                Thread.sleep(random.nextInt(2000));

            }catch (InterruptedException e  ){

            }
        }
        message.write("finished");
    }


}

class Reader implements Runnable{
    private Message message;
    public Reader(Message message){
        this.message = message;
    }
    public void run(){
        Random random = new Random();
        for (String latestMessage = message.read(); !latestMessage.equals("finished");
             latestMessage = message.read()){
            System.out.println(latestMessage);
            try{
                Thread.sleep(random.nextInt(2000));
            }catch ( InterruptedException e ){

            }
        }
    }
}