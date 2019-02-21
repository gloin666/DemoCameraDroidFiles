package ehb.be.democameradroidfiles;

import java.io.Serializable;

public class ObjectSentDroid implements Serializable {
    private String inhoudString;

    public ObjectSentDroid(String inhoudString) {
        this.inhoudString = inhoudString;
    }

    public String getInhoudString() {
        return inhoudString;
    }

    public void setInhoudString(String inhoudString) {
        this.inhoudString = inhoudString;
    }
}
