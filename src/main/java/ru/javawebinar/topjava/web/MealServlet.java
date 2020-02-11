package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.impl.MemoryMealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MealServlet extends HttpServlet {

    private MealDao mealDao;

    public MealServlet() {
        this.mealDao = new MemoryMealDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Meal> mealList = mealDao.getAll();
        List<MealTo> mealToList = MealsUtil.filteredByLoop(mealList, LocalTime.MIN, LocalTime.MAX, 2000);

        String action = request.getParameter("action");
        if (action == null) action = "mealList";

        switch (action) {
            case "delete":
                mealDao.delete(Integer.parseInt(request.getParameter("mealId")));
                response.sendRedirect(request.getContextPath() + "/meals");
                break;
            case "edit":
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                MealTo mealTo;
                for (MealTo elem : mealToList) {
                    if (elem.getId() == mealId) {
                        mealTo = mealToList.get(mealToList.indexOf(elem));
                        request.setAttribute("meal", mealTo);
                        request.getRequestDispatcher("/meal.jsp")
                                .forward(request, response);
                        return;
                    }
                }
                break;
            case "insert":
                response.sendRedirect(request.getContextPath() + "/meal.jsp");
                break;
            default:
                request.setAttribute("meals", mealToList);
                request.getRequestDispatcher("/meals.jsp")
                        .forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        Meal meal = new Meal(
                LocalDateTime.parse(
                        request.getParameter("localDateTime"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
                ),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );

        String mealId = request.getParameter("id");
        if (mealId == null || mealId.isEmpty()) {
            mealDao.add(meal);
        } else {
            int id = Integer.parseInt(mealId);
            meal.setId(id);
            mealDao.update(meal, id);
            System.out.println(meal.getDescription() + " " + meal.getId() + " " + id);
        }

        response.sendRedirect(request.getContextPath() + "/meals");
    }
}
