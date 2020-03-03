package common;

public class Question extends Post {
        private int answercount;
        private String title;
        private String tags;

        public Question() {
            super();
            this.answercount = 0;
            this.title = null;
            this.tags = null;
        }

        public Question(int answercount, String title, String tags) {
            this.answercount = answercount;
            this.title = title;
            this.tags = tags;
        }

        public Question(Question q) {
            super(q);
            this.answercount = q.getAnswercount();
            this.title = q.getTitle();
            this.tags = q.getTags();

        }

        public int getAnswercount() {
            return answercount;
        }

        public String getTitle() {
            return title;
        }

        public String getTags() {
            return tags;
        }

        public void setAnswercount(int answercount) {
            this.answercount = answercount;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public Question clone() {
            return new Question(this);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(" Answer_Count: ")
                    .append(getAnswercount())
                    .append(" Title: ")
                    .append(getTitle())
                    .append(" Tags: ")
                    .append(getTags());
            return super.toString() + sb.toString();
        }
}
