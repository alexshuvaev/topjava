package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.impl.MemoryMealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        String forward;
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealDao.delete(mealId);
            response.sendRedirect(request.getContextPath() + "/meals?action=mealList");
            return;
        } else if (action.equalsIgnoreCase("edit")) {

            int mealId = Integer.parseInt(request.getParameter("mealId"));
            MealTo mealTo = mealToList.get(mealId);
            forward = "/meal.jsp";
            request.setAttribute("meal", mealTo);
        } else if (action.equalsIgnoreCase("mealList")) {
            forward = "/meals.jsp";
            request.setAttribute("meals", mealToList);
        } else {
            forward = "/meal.jsp";
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        Meal meal = new Meal();
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        try {
           LocalDateTime localDateTime = TimeUtil.convertToLocalDateTimeViaInstant(
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(request.getParameter("localDateTime")
                    ));

            meal.setDateTime(localDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String mealId = request.getParameter("id");
        if (mealId == null || mealId.isEmpty()) {
            mealDao.add(meal);
        } else {
            int id = Integer.parseInt(mealId);
            mealDao.update(meal, id);
        }

        response.sendRedirect("meals?action=mealList");
    }
}
