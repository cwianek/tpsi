package dao;

import com.mongodb.MongoClient;
import models.Indexes;
import models.Mark;
import models.Student;
import models.Subject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;

public class MongoDao implements IDao {
    private static MongoDao mongoDao;
    private final static Morphia morphia = new Morphia();
    private static Datastore datastore;

    private MongoDao(){
        morphia.mapPackage("model");
        datastore = morphia.createDatastore(new MongoClient("localhost", 27017), "TPSI");
        datastore.ensureIndexes();
        Indexes indexes = datastore.createQuery(Indexes.class).get();
        if (indexes == null) {
            datastore.save(new Indexes());
        }
    }

    public synchronized static MongoDao getInstance(){
        if(mongoDao == null){
            mongoDao = new MongoDao();
        }
        return mongoDao;
    }

    //students

    @Override
    public List<Student> getStudents() {
         return datastore.createQuery(Student.class).asList();
    }

    @Override
    public Student getStudent(int index) {
        return datastore.find(Student.class, "index", index).get();
    }

    @Override
    public Student saveStudent(Student student) {
        student.setIndex(getNextStudentIndex());
        datastore.save(student);
        return student;
    }

    @Override
    public void updateStudent(Student student) {
        Student current = datastore.find(Student.class, "index", student.getIndex()).get();
        student.setId(current.getId());
        datastore.save(student);
    }

    //subjects

    @Override
    public List<Subject> getSubjects() {
        return datastore.createQuery(Subject.class).asList();
    }

    @Override
    public Subject getSubject(ObjectId id) {
        return datastore.find(Subject.class, "id", id).get();
    }

    @Override
    public Subject saveSubject(Subject subject) {
        Key<Subject> key =  datastore.save(subject);
        subject.setId((ObjectId) key.getId());
        return subject;
    }

    @Override
    public void updateSubject(Subject subject) {
        Subject current = datastore.find(Subject.class, "id", subject.getId()).get();
        subject.setId(current.getId());
        datastore.save(subject);
    }

    @Override
    public int getNextStudentIndex() {
        Query<Indexes> query = datastore.createQuery(Indexes.class).limit(1);
        UpdateOperations<Indexes> updateOperation = datastore.createUpdateOperations(Indexes.class).inc("index");
        return datastore.findAndModify(query, updateOperation, false, false).getIndex();
    }

    @Override
    public void delete(Object object) {
        datastore.delete(object);
    }
}
