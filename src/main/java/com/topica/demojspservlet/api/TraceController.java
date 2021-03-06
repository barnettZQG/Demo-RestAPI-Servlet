package com.topica.demojspservlet.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topica.demojspservlet.model.Todo;
import com.topica.demojspservlet.utils.data.RequestResponseDataUtil;

@WebServlet(name = "TraceController", urlPatterns = { "/api/req" })
public class TraceController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = "http://todos/api/todos";
        URL url = new URL(address);
        URLConnection conn = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            result.append(line);
        }
        in.close();
        JSONObject json = JSONObject.parseObject(result.toString());// 把String对象转为JSONObject对象
        List<Todo> todoList = new ArrayList<>();
        JSONArray jsonarray = json.getJSONArray("todoList");
        for (int i = 0; i < jsonarray.size(); i++) {
            Todo todo = new Todo();
            JSONObject jo = jsonarray.getJSONObject(i);
            todo.setId(jo.getLong("id"));
            todo.setName(jo.getString("name"));
            todo.setDescription(jo.getString("description"));
            todoList.add(todo);
        }
        resp.setCharacterEncoding("utf-8");
        RequestResponseDataUtil.sendAsJson(resp, todoList);
    }
}