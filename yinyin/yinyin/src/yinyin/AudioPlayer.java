package yinyin;

import java.io.File;
import java.io.InputStream;

public interface AudioPlayer {
	abstract public void play();
	abstract public void play(File file);
}
