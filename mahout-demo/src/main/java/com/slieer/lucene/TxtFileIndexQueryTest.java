package com.slieer.lucene;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

public class TxtFileIndexQueryTest {
    private static Logger logger = LogManager.getLogger(TxtFileIndexQueryTest.class
            .getName());
    private static final String FIELD_CONTENTS = "contents";
    private static final String FIELD_PATH = "path";

    private static String indexDirStr = "src/main/resources/lucene/index"; 
    private static String dataDirStr = "src/main/resources/lucene/data";
    private static boolean isCreate = true;
    
    @Test
    public void testDir(){
        File f = new File("");
        logger.info("file path test. {}", f.getAbsolutePath());
        
        File ff =new File("src/main/resources/lucene/data");
        logger.info("file path. {}", ff.getAbsolutePath());
    }
    
    @Test
    public void testIndex(){
        
        try {
            testCreateIndex(indexDirStr, dataDirStr, isCreate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testQuery(){
        //StringindexFileDir
        String queryField = FIELD_CONTENTS;
        String queryStr = "科比";
        try {
            testQueryIndex(indexDirStr, queryField, queryStr);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    
    public static void testCreateIndex(String indexDirStr, String dataDirStr,
            boolean isCreate/* Or Appdned Index */) throws Exception {
        File indexDir = new File(indexDirStr);
        File dataDir = new File(dataDirStr);
        
        
        File[] dataFiles = dataDir.listFiles();

        Directory dir = FSDirectory.open(indexDir);
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47,
                analyzer);

        if (isCreate) {
            iwc.setOpenMode(OpenMode.CREATE);
        } else {
            iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
        }

        IndexWriter indexWriter = new IndexWriter(dir, iwc);
        long startTime = new Date().getTime();
        for (int i = 0; i < dataFiles.length; i++) {
            if (dataFiles[i].isFile()
                    && dataFiles[i].getName().endsWith(".txt")) {
                System.out.println("Indexing file "
                        + dataFiles[i].getCanonicalPath());

                Document document = new Document();

                Field pathField = new StringField(FIELD_PATH,
                        dataFiles[i].getPath(), Field.Store.YES);
                Reader txtReader = new FileReader(dataFiles[i]);
                Field contentField = new TextField(FIELD_CONTENTS, txtReader);
                document.add(pathField);
                document.add(contentField);
                indexWriter.addDocument(document);
            }
        }
        indexWriter.close();
        long endTime = new Date().getTime();

        System.out.println("It takes " + (endTime - startTime)
                + " milliseconds to create index for the files in directory "
                + dataDir.getPath());
    }

    public static void testQueryIndex(String indexFileDir, String queryField,
            String queryStr) throws IOException, ParseException {
        File indexDir = new File(indexFileDir);
        IndexReader reader = DirectoryReader.open(FSDirectory.open(indexDir));
        IndexSearcher searcher = new IndexSearcher(reader);

        if (!indexDir.exists()) {
            System.out.println("The Lucene index is not exist");
            return;
        }

        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
        QueryParser parser = new QueryParser(Version.LUCENE_47, queryField,
                analyzer);

        Query query = parser.parse(queryStr);
        TopDocs docs = searcher.search(query, null, 10);
        ScoreDoc[] doc = docs.scoreDocs;
        // Hits hits = searcher.search(luceneQuery);
        for (int i = 0; i < docs.totalHits; i++) {

            System.out.println("File: " + doc[i]);
        }
    }
}