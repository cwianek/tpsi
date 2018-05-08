package dao;

import com.mongodb.MongoClient;
import lombok.Getter;
import models.Indexes;
import models.Student;
import models.Subject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;


public class MongoDao implements IDao {
    @Getter
    protected String mongoName = "TPSI";
    protected static IDao mongoDao;
    private final static Morphia morphia = new Morphia();
    private static Datastore datastore;

    protected MongoDao() {
        morphia.mapPackage("model");
        datastore = morphia.createDatastore(new MongoClient("localhost", 27017), getMongoName());
        datastore.ensureIndexes();
        Indexes indexes = datastore.createQuery(Indexes.class).get();
        if (indexes == null) {
            datastore.save(new Indexes());
        }
    }

    public synchronized static IDao getInstance() {
        if (mongoDao == null) {
            mongoDao = new MongoDao();
        }
        return mongoDao;
    }

    //students

    @Override
    public List<Student> getStudents(MultivaluedMap<String, String> params) {
        Query<Student> query = datastore.createQuery(Student.class);
        for (String key : new String[]{"name", "surname"}) {
            if (params.containsKey(key))
                query.field(key).containsIgnoreCase(params.getFirst(key));
        }
        String birthdate = "birthdate";
        if(params.containsKey(birthdate)){
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(params.getFirst(birthdate));
            } catch (ParseException e) {
                throw new BadRequestException();
            }
            String op = "";
            if(params.containsKey("op")){
                op = params.getFirst("op");
            }
            switch (op){
                case "gt":
                    query.field(birthdate).greaterThan(date);
                    break;
                case "lt":
                    query.field(birthdate).lessThan(date);
                    break;
                default:
                    query.field(birthdate).equal(date);
            }

        }
        return query.asList();
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
    public List<Subject> getSubjects(MultivaluedMap<String,String> params) {
        Query<Subject> query = datastore.createQuery(Subject.class);
        String lecturer = "lecturer";
        if(params.containsKey(lecturer)){
            query.field(lecturer).containsIgnoreCase(params.getFirst(lecturer));
        }
        return query.asList();
    }

    @Override
    public Subject getSubject(ObjectId id) {
        return datastore.find(Subject.class, "id", id).get();
    }

    @Override
    public Subject saveSubject(Subject subject) {
        Key<Subject> key = datastore.save(subject);
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
        UpdateOperations<Indexes> updateOperation = datastore.createUpdateOperations(Indexes.class).inc("studentIndex");
        return datastore.findAndModify(query, updateOperation, false, false).getStudentIndex();
    }

    @Override
    public int getNextMarkId() {
        Query<Indexes> query = datastore.createQuery(Indexes.class).limit(1);
        UpdateOperations<Indexes> updateOperation = datastore.createUpdateOperations(Indexes.class).inc("markId");
        return datastore.findAndModify(query, updateOperation, false, false).getMarkId();
    }


    @Override
    public void delete(Object object) {
        datastore.delete(object);
    }
}
