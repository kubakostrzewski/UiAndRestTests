package rest.utils;

public class RequestArguments {
    public enum SortBy {

        TIME("time"),
        VIRAL("viral"),
        TOP("top");

        private String sort;

        SortBy(String sortBy) {
            this.sort = sortBy;
        }

        public String getArgument() {
            return sort;
        }

    }
    public enum DateRange{
        DAY("day"),
        MONTH("month"),
        YEAR("year"),
        ALL("all");

        private String range;

        DateRange(String dateRange){
            this.range = dateRange;
        }

        public String getArgument() {
            return range;
        }
    }

}
