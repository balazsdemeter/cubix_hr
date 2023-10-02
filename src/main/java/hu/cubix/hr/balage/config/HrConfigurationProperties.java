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
        private double year_max;
        private double year_med;
        private double year_min;

        public double getYear_max() {
            return year_max;
        }

        public void setYear_max(double year_max) {
            this.year_max = year_max;
        }

        public double getYear_med() {
            return year_med;
        }

        public void setYear_med(double year_med) {
            this.year_med = year_med;
        }

        public double getYear_min() {
            return year_min;
        }

        public void setYear_min(double year_min) {
            this.year_min = year_min;
        }
    }

    public static class Percent {
        private double percent_year_max;
        private double percent_year_med;
        private double percent_year_min;
        private double percent_default;

        public double getPercent_year_max() {
            return percent_year_max;
        }

        public void setPercent_year_max(double percent_year_max) {
            this.percent_year_max = percent_year_max;
        }

        public double getPercent_year_med() {
            return percent_year_med;
        }

        public void setPercent_year_med(double percent_year_med) {
            this.percent_year_med = percent_year_med;
        }

        public double getPercent_year_min() {
            return percent_year_min;
        }

        public void setPercent_year_min(double percent_year_min) {
            this.percent_year_min = percent_year_min;
        }

        public double getPercent_default() {
            return percent_default;
        }

        public void setPercent_default(double percent_default) {
            this.percent_default = percent_default;
        }
    }
}
