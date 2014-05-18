package com.slieer.lucene.lucenesearch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexDeletionPolicy;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.KeepOnlyLastCommitDeletionPolicy;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * 使用 Apache Lucene 搜索文本
 * https://www.ibm.com/developerworks/cn/opensource/os-apache-lucenesearch/
 * 
 * This class reads the input files from the data directory, creates indexes and
 * writes them in the index directory
 * 
 * @author Amol
 * 
 */
public class Indexer {
    private static final String FIELD_MESSAGE = "message";

    private static final String FIELD_SUBJECT = "subject";

    private static final String FIELD_MONTH = "month";

    private static final String FIELD_DATE = "date";

    private static final String FIELD_RECEIVER = "receiver";

    private static final String FIELD_SENDER = "sender";

    private IndexWriter indexWriter;

    /* Location of directory where index files are stored */
    private String indexDirectory;

    /* Location of data directory */
    private String dataDirectory;

    private boolean isCreate;

    Indexer(String indexDirectory, String dataDirectory) {
        this.indexDirectory = indexDirectory;
        this.dataDirectory = dataDirectory;
    }

    /**
     * This method creates an instance of IndexWriter which is used to add
     * Documents and write indexes on the disc.
     */
    void createIndexWriter() {
        if (indexWriter == null) {
            try {
                // Create instance of Directory where index files will be stored
                Directory fsDirectory = FSDirectory.open(new File(
                        indexDirectory));
                /*
                 * Create instance of analyzer, which will be used to tokenize
                 * the input data
                 */
                IndexWriterConfig iwc = constructConfig();
                if (isCreate) {
                    iwc.setOpenMode(OpenMode.CREATE);
                } else {
                    iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
                }

                indexWriter = new IndexWriter(fsDirectory, iwc);
            } catch (IOException ie) {
                System.out.println("Error in creating IndexWriter");
                throw new RuntimeException(ie);
            }
        }
    }

    static IndexWriterConfig constructConfig() {
        Analyzer standardAnalyzer = new StandardAnalyzer(Version.LUCENE_47);

        // Create the instance of deletion policy
        IndexDeletionPolicy deletionPolicy = new KeepOnlyLastCommitDeletionPolicy();

        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47,
                standardAnalyzer);

        iwc.setIndexDeletionPolicy(deletionPolicy);
        return iwc;
    }

    /**
     * This method reads data directory and loads all properties files. It
     * extracts various fields and writes them to the index using IndexWriter.
     * 
     * @throws IOException
     * @throws FileNotFoundException
     */
    void indexData() throws FileNotFoundException, IOException {

        File[] files = getFilesToBeIndxed();
        for (File file : files) {
            Properties properties = new Properties();
            properties.load(new FileInputStream(file));

            /* Step 1. Prepare the data for indexing. Extract the data. */
            String sender = properties.getProperty(FIELD_SENDER);
            String receiver = properties.getProperty(FIELD_RECEIVER);
            String date = properties.getProperty(FIELD_DATE);
            String month = properties.getProperty(FIELD_MONTH);
            String subject = properties.getProperty(FIELD_SUBJECT);
            String message = properties.getProperty(FIELD_MESSAGE);
            String emaildoc = file.getAbsolutePath();

            /* Step 2. Wrap the data in the Fields and add them to a Document */
            /*
             * We plan to show the value of sender, subject and email document
             * location along with the search results,for this we need to store
             * their values in the index
             */
            Field senderField = new StringField(FIELD_SENDER, sender,
                    Field.Store.NO);

            Field receiverfield = new StringField(FIELD_RECEIVER, receiver,
                    Field.Store.NO);

            Field subjectField = new TextField(FIELD_SUBJECT, subject,
                    Field.Store.YES);

            if (subject.toLowerCase().indexOf("pune") != -1) {
                // Display search results that contain pune in their subject
                // first by setting boost factor
                subjectField.setBoost(2.2F);
            }

            Field emaildatefield = new StringField(FIELD_DATE, date,
                    Field.Store.NO);

            Field monthField = new StringField(FIELD_MONTH, month,
                    Field.Store.NO);

            Field messagefield = new TextField(FIELD_MESSAGE, message,
                    Field.Store.NO);

            Field emailDocField = new StringField("emailDoc", emaildoc,
                    Field.Store.YES);

            // Add these fields to a Lucene Document
            Document doc = new Document();
            doc.add(senderField);
            doc.add(receiverfield);
            doc.add(subjectField);
            doc.add(emaildatefield);
            doc.add(monthField);
            doc.add(messagefield);
            doc.add(emailDocField);
            if (sender.toLowerCase().indexOf("job") != -1) {
                // Display search results that contain 'job' in their sender
                // email address
                // doc.setBoost(2.1F);
                //You cannot set an index-time boost on an unindexed field, 
                //or one that omits norms
                /*
                 *要给StringField型字段设置boost： 1、要加索引； 2、字段类型Field.Store.NO
                 */
                senderField.setBoost(2.1F);

            }

            // Step 3: Add this document to Lucene Index.
            indexWriter.addDocument(doc);
        }
        /*
         * Requests an "optimize" operation on an index, priming the index for
         * the fastest available search
         */
        // indexWriter.optimize();
        /*
         * Commits all changes to the index and closes all associated files.
         */
        indexWriter.close();
    }

    private File[] getFilesToBeIndxed() {
        File dataDir = new File(dataDirectory);
        if (!dataDir.exists()) {
            throw new RuntimeException(dataDirectory + " does not exist");
        }
        File[] files = dataDir.listFiles();
        return files;
    }

}
