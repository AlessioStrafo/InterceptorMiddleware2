package co.develhope.InterceptorMiddleware2.Interceptors;

import co.develhope.InterceptorMiddleware2.Entities.Month;
import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class MonthInterceptor implements HandlerInterceptor {

    public static List<Month> months = new ArrayList<>(Arrays.asList(
            new Month(1, "July", "Luglio", "Juli"),
            new Month(2, "August", "Agosto", "August"),
            new Month(3, "September", "Settembre", "September"),
            new Month(4, "October", "Ottobre", "Oktober"),
            new Month(5, "November", "Novembre", "November"),
            new Month(6, "December", "Dicembre", "Dezember")
    ));
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String monthNumberString = request.getHeader("monthNumber");
        if (monthNumberString == null || monthNumberString.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "monthNumber is missing");
            return false;
        }
        int monthNumber;
        try {
            monthNumber = Integer.parseInt(monthNumberString);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid month Id format");
            return false;
        }
        Optional<Month> optionalMonth = months.stream().filter(singleMonth -> singleMonth.getMonthNumber() == monthNumber).findFirst();
        if (optionalMonth.isPresent()) {
            request.setAttribute("MonthInterceptor - month", optionalMonth.get());
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            Month invalidMonth = new Month();
            invalidMonth.setMonthNumber(monthNumber);
            invalidMonth.setEnglishName("nope");
            invalidMonth.setItalianName("nope");
            invalidMonth.setGermanName("nope");
            request.setAttribute("MonthInterceptor - month", invalidMonth);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        return true;
    }
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        if (ex != null) {
            System.out.println("The exception is: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
