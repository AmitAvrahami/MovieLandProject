package com.hit.server;

import java.util.Map;

public class Request {

    private String m_action;
    private Map<String,Object> m_body;

    public Request(String action, Map<String, Object> body) {
        this.m_action = action;
        this.m_body = body;
    }

    public String getAction() {
        return m_action;
    }

    public void setAction(String whatToDo) {
        this.m_action = whatToDo;
    }

    public Map<String, Object> getBody() {
        return m_body;
    }

    public void setBody(Map<String, Object> body) {
        this.m_body = body;
    }
}
