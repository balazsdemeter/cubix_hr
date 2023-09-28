package hu.cubix.hr.balage.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "hr")
@Component
public class HrConfigurationProperties {
    private Year year;
    private Percent percent;

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Percent getPercent() {
        return percent;
    }

    public void setPercent(Percent percent) {
        this.percent = percent;
    }

    public static class Year {
        private double year_1;
        private double year_2;
        private double year_3;

        public double getYear_1() {
            return year_1;
        }

        public void setYear_1(double year_1) {
            this.year_1 = year_1;
        }

        public double getYear_2() {
            return year_2;
        }

        public void setYear_2(double year_2) {
            this.year_2 = year_2;
        }

        public double getYear_3() {
            return year_3;
        }

        public void setYear_3(double year_3) {
            this.year_3 = year_3;
        }
    }

    public static class Percent {
        private double percent_year_1;
        private double percent_year_2;
        private double percent_year_3;
        private double percent_default;

        public double getPercent_year_1() {
            return percent_year_1;
        }

        public void setPercent_year_1(double percent_year_1) {
            this.percent_year_1 = percent_year_1;
        }

        public double getPercent_year_2() {
            return percent_year_2;
        }

        public void setPercent_year_2(double percent_year_2) {
            this.percent_year_2 = percent_year_2;
        }

        public double getPercent_year_3() {
            return percent_year_3;
        }

        public void setPercent_year_3(double percent_year_3) {
            this.percent_year_3 = percent_year_3;
        }

        public double getPercent_default() {
            return percent_default;
        }

        public void setPercent_default(double percent_default) {
            this.percent_default = percent_default;
        }
    }
}
