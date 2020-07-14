package com.proj2; 

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
public class TicketSound {

   public void start() {
      File bgm;
      AudioInputStream stream;
      AudioFormat format;
      DataLine.Info info;

      bgm = new File("D:\\ts.wav"); // 사용시에는 개별 폴더로 변경할 것

      Clip clip;

      try {
         stream = AudioSystem.getAudioInputStream(bgm);
         format = stream.getFormat();
         info = new DataLine.Info(Clip.class, format);
         clip = (Clip)AudioSystem.getLine(info);
         clip.open(stream);
         clip.start();

      } catch (Exception e) {

      }

   }

   public void end() { //꺼지는 소리
      File bgm;
      AudioInputStream stream;
      AudioFormat format;
      DataLine.Info info;

      bgm = new File("D:\\end.wav");

      Clip clip;

      try {
         stream = AudioSystem.getAudioInputStream(bgm);
         format = stream.getFormat();
         info = new DataLine.Info(Clip.class, format);
         clip = (Clip) AudioSystem.getLine(info);
         clip.open(stream);
         clip.start();
      } catch (Exception e) {

      }

   }

}