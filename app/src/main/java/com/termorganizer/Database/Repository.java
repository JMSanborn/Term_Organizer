package com.termorganizer.Database;

import android.app.Application;

import com.termorganizer.DAO.CourseDAO;
import com.termorganizer.DAO.TermDAO;
import com.termorganizer.Entity.Course;
import com.termorganizer.Entity.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
        private TermDAO mTermDAO;
        private CourseDAO mCourseDAO;
        private List<Term> mAllTerms;
        private List<Course> mAllCourses;


        private static int NUMBER_OF_THREADS = 4;
        static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        public Repository(Application application) {
            TermDatabase db = TermDatabase.getDatabase(application);
            mTermDAO = db.termDAO();
            mCourseDAO = db.courseDAO();
        }

        //Terms
        public List<Term> getAllTerms() {
            databaseExecutor.execute(() -> {
                mAllTerms = mTermDAO.getAllTerms();
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return mAllTerms;
        }

        public void update (Term term){
            databaseExecutor.execute(()->{
                mTermDAO.update(term);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void delete (Term term){
            databaseExecutor.execute(()->{
                mTermDAO.delete(term);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void insert(Term term){
            databaseExecutor.execute(()->{
                mTermDAO.insert(term);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Courses
        public List<Course>getAllCourses(){
            databaseExecutor.execute(()->{
                mAllCourses=mCourseDAO.getAllCourses();
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return mAllCourses;
        }
        public void update(Course course) {
            databaseExecutor.execute(() -> {
                mCourseDAO.update(course);
            });
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void delete(Course course) {
            databaseExecutor.execute(() -> {
                mCourseDAO.delete(course);
            });
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void insert(Course course) {
            databaseExecutor.execute(() -> {
                mCourseDAO.insert(course);
            });
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Applications

}
