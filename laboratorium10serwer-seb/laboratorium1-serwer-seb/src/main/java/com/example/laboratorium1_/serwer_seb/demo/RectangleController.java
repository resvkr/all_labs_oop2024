package com.example.laboratorium1_.serwer_seb.demo;


import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// Контролер REST для обробки запитів, що стосуються прямокутника
@RestController
@RequestMapping("/api/rectangles")// Базовий шлях для всіх методів цього контролера
public class RectangleController {

    private List<Rectangle> rectangles = new ArrayList<>();


    // Конструктор для додавання зразкових прямокутників (опційно)
    public RectangleController() {
        rectangles.add(new Rectangle(10, 20, 100, 50, "blue"));
    }

    // Метод для додавання прямокутника з певними параметрами
    @PostMapping
  public  String addRectangle(@RequestBody RectangleRequest rectangleRequest) {
        Rectangle rectangle = new Rectangle(
                rectangleRequest.getX(), rectangleRequest.getY(), rectangleRequest.getWidth(), rectangleRequest.getHeight(), rectangleRequest.getColor()
        );
        rectangles.add(rectangle);
        return "Rectangle added successfully";
    }

    @GetMapping
    public List<Rectangle> getAllRectangles() {
        return rectangles;// Spring автоматично конвертує список у JSON
    }

    @GetMapping("/svg")
    public String getRectanglesSVG() {
        StringBuilder svg = new StringBuilder();

        svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\">\n");

        for (Rectangle rectangle : rectangles) {
            svg.append(String.format("<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"%s\"/>\n",
                    rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), rectangle.getColor()
            ));
        }
        svg.append("</svg>");

        return svg.toString();
    }

    // Метод для отримання прямокутника за індексом
    @GetMapping("/{index}")
    public Rectangle getRectangleByIndex(@PathVariable int index){
        if(index>=0 && index<rectangles.size()){
            return rectangles.get(index);
        }else {
            throw new IllegalArgumentException("Rectangle at index \" + index + \" not found");

        }
    }

    @PutMapping("/{index}")
    public String updateRectangle(@PathVariable int index, @RequestBody RectangleRequest updatedRectangle){
        if (index>=0&&index<rectangles.size()){
            Rectangle rectangle = new Rectangle(
                    updatedRectangle.getX(),
                    updatedRectangle.getY(),
                    updatedRectangle.getWidth(),
                    updatedRectangle.getHeight(),
                    updatedRectangle.getColor()
            );
            rectangles.set(index, rectangle);
            return "Rectangle at index " + index + " updated successfully";
        }else {
            throw new IndexOutOfBoundsException("Rectangle at index " + index + " not found");
        }
    }

    @DeleteMapping("/{index}")
    public String deleteRectangle(@PathVariable int index){
        if (index>=0&&index<rectangles.size()){
            rectangles.remove(index);
            return "Rectangle at index " + index + " deleted successfully";
        }else {
            throw new IndexOutOfBoundsException("Rectangle at index " + index + " not found");
        }
    }
}
