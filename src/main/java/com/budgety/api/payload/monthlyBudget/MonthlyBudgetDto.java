package com.budgety.api.payload.monthlyBudget;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class MonthlyBudgetDto {
    private static final SimpleDateFormat dateFormat
            = new SimpleDateFormat("dd-MM-yyyy");
    private Long id;
    @JsonFormat(pattern="dd-MM-yyyy")
    private String startDate;
    @JsonFormat(pattern="dd-MM-yyyy")
    private String endDate;

    public void setStartDate(Date date){
        this.startDate = dateToString(date);
    }

    public void setEndDate(Date date){
        this.endDate = dateToString(date);
    }
    public void setEndDate(String string){
        this.endDate=string;
    }

    public Date getStartDate() throws ParseException {
        return stringToDate(this.startDate);
    }

    public Date getEndDate() throws ParseException {
        return stringToDate(this.endDate);
    }

    // Convert methods
    private Date stringToDate(String inputString) throws ParseException {
        return dateFormat.parse(inputString);
    }

    private String dateToString(Date inputDate){
        return dateFormat.format(inputDate);
    }

}
