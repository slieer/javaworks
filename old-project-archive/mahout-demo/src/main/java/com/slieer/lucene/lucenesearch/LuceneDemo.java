package com.slieer.lucene.lucenesearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.simple.SimpleQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixFilter;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;
import org.junit.Test;

/**
 * 使用 Apache Lucene 搜索文本
 * https://www.ibm.com/developerworks/cn/opensource/os-apache-lucenesearch/

 * 
 * This class demonstrates the usage of Lucene's Indexing and Searching APIs.
 * The sample code comes with a set of input property files that represent
 * email messages of a user. This class has various methods that read the input
 * property files, indexes various fields and provides search on the 
 * indexed data.
 *
 * @author  Amol Sonawane
 */
public class LuceneDemo {
	//a path to directory where Lucene will store index files
	private static String indexDirectory = "src/main/resources/lucene/index1";
	// a path to directory which contains data files that need to be indexed
	private static String dataDirectory = "src/main/resources/lucene/data1";;
	
	private static IndexSearcher indexSearcher;
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testCreateIndex() throws FileNotFoundException, IOException {
		//create Lucene index
		LuceneDemo.createLuceneIndex();
	}
	
	@Test
	public void testSearch(){
        //create IndexSearcher
        try {
            LuceneDemo.createIndexSearcher();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LuceneDemo luceneDemo = new LuceneDemo();
        luceneDemo.termQueryExample();
//        luceneDemo.rangeQueryExample();
//        luceneDemo.prefixQueryExample();
//        luceneDemo.booleanQueryExample();
//        luceneDemo.phraseQueryExample();
//        luceneDemo.wildCardQueryExample();
//        luceneDemo.fuzzyQueryExample();
//        luceneDemo.queryParserExample();
//        luceneDemo.fieldBoostFactorExample();
//        luceneDemo.sortBySenderExample();
//        luceneDemo.filterExample();
//        luceneDemo.deletDocumentFromIndex();
	}
	
	/**
	 * 创建索引文件
	 */
	static void createLuceneIndex(){
		Indexer indexer = new Indexer(indexDirectory,dataDirectory);
		//Create IndexWriter
		indexer.createIndexWriter();
		try {
			//Index data
			indexer.indexData();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 创建搜索器
	 * @throws CorruptIndexException
	 * @throws IOException
	 */
	static void createIndexSearcher() throws CorruptIndexException, IOException{
        File indexDir = new File(indexDirectory);
        IndexReader reader = DirectoryReader.open(FSDirectory.open(indexDir));
		/* Create instance of IndexSearcher 
		 */
		indexSearcher = new IndexSearcher(reader);
	}
		
	/**
	 * 关键字搜索
	 * Searches mails that contain the word "java" in subject field.
	 */
	void termQueryExample(){
		System.out.println("TermQuery example: Search mails having the word \"java\"" +
				" in the subject field");
		Term term = new Term("subject","java");
		Query query = new TermQuery(term);	    
	    showSearchResults(query);
	}
	
	/**
	 * 范围搜索
	 * Searches mails received between 01/06/2009 to 6/06/2009 both inclusive
	 */
	void rangeQueryExample(){
		System.out.println("RangeQuery example: Search mails from 01/06/2009 " +
				"to 6/06/2009 both inclusive");
		//Term begin = new Term("date","20090601");
	    //Term end = new Term("date","20090606");
	    //Query query = new RangeQuery(begin, end, true);
		
		String field = "date";
		String beginDate = "20090601";
		String endDate = "20090606";

		BytesRef begin = new BytesRef(beginDate.getBytes());
        BytesRef end = new BytesRef(endDate.getBytes());
        Query query = new TermRangeQuery(field, begin, end, true, true);
	    showSearchResults(query);
	}
	
	/**
	 * 前缀搜索
	 * Searches mails having sender field prefixed by the word "job"
	 */
	void prefixQueryExample(){
		System.out.println("PrefixQuery example: Search mails having sender field prefixed by the word 'job'");
		PrefixQuery query = new PrefixQuery(new Term("sender","job"));
	    showSearchResults(query);
	}
	
	/**
	 * 多关键字搜索
	 * 	Searches mails that contain both "java" and "bangalore" in the subject field   
	 */
	void booleanQueryExample(){
		System.out.println("BooleanQuery: Search mails that have both 'java' " +
				"and 'bangalore' in the subject field ");
		Query query1 = new TermQuery(new Term("subject","java"));
		Query query2 = new TermQuery(new Term("subject","bangalore"));
		BooleanQuery query = new BooleanQuery();
		query.add(query1,BooleanClause.Occur.MUST);
		query.add(query2,BooleanClause.Occur.MUST);
		showSearchResults(query);
	}
	
	/**
	 * 
	 * Searches mails that contain a give phrase in the subject field.
	 */
	void phraseQueryExample(){
		System.out.println("PhraseQuery example: Search mails that have phrase " +
				"'job opening j2ee' in the subject field.");
		PhraseQuery query = new PhraseQuery();
		query.setSlop(1);
		//Add terms of the phrases.
		query.add(new Term("subject","job"));
		query.add(new Term("subject","opening"));
		query.add(new Term("subject","j2ee"));
		
		showSearchResults(query);
	}
	
	/**
	 * 单关键字，带通配符搜索
	 * Searches mails that have word 'architect' in subject field.
	 */
	void wildCardQueryExample(){
		System.out.println("WildcardQuery: Search for 'arch*' to find emails that " +
				"have word 'architect' in subject field.");
		Query query = new WildcardQuery(new Term("subject","arch*"));
		showSearchResults(query);
	}
	
	/**
	 * Searches for emails that have word similar to 'admnistrtor' in the 
	 * subject field. Note that we have misspelled the word and looking for
	 * a word that is a close match to this.
	 */
	void fuzzyQueryExample(){
		System.out.println("FuzzyQuery: Search for emails that have word similar " 
		   +"to 'admnistrtor' in the subject field. Note we have misspelled administrator here.");
		Query query = new FuzzyQuery(new Term("subject", "admnistrtor"));
		 showSearchResults(query);
	}
	
	/**
	 * Shows how to use QueryParser
	 */
	void queryParserExample(){
		//first argument is the default field for query terms
		System.out.println("QueryParser: Searches for mails that have given user" +
				" entered query expression in the subject field.");
		SimpleQueryParser queryParser = new SimpleQueryParser(new StandardAnalyzer(Version.LUCENE_47), "subject");
		
			/* Searches for emails that contain the words 'job openings'
			 *  and '.net' and 'pune'
			 */
//		try {
//		} catch (ParseException e) {
//		    e.printStackTrace();
//		}
			Query query = queryParser.parse("job openings AND .net AND pune");
			showSearchResults(query);
	}
	
	void queryResultsSortingExample() throws IOException{
		Query query = new TermQuery(new Term("java"));
		//Filter filter = new TermsFilter();
		TopDocs topDocs = indexSearcher.search(query, 10);
	}
	
	/**
	 * Delete all the mails from the index that were received in May 2009.
	 */
	void deletDocumentFromIndex(){
		try {
			//Check how many emails received in May 2009
			Query query = new WildcardQuery(new Term("month","05"));
			System.out.println("---------------------------------------------");
			System.out.println("\nSearching for mails that were received in May");
			showSearchResults(query);			

			IndexWriterConfig config = Indexer.constructConfig();
			IndexWriter writer = new IndexWriter(FSDirectory.open(new File(indexDirectory)), config);
		    writer.deleteDocuments(new Term("name", "11.txt"));
		    writer.close();			
			
			createIndexSearcher();
			System.out.println("After deleting mails received in May, " +
					"searching for mails that were received in May");
			showSearchResults(query);
			
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Shows the result of boosting fields.
	 */
	void fieldBoostFactorExample(){
		/*Searches mails that have word 'job' in their subject field, giving
		  importance to mails having word 'pune' See Indexer.java line 102
		 */
		System.out.println("Boosting fields and documents: Searches mails that" +
				" have 'job' in their subject field, giving more importance to " +
				" mails having 'pune' in their subject field");
		WildcardQuery query = new WildcardQuery(new Term("subject","job*"));
		showSearchResults(query);
	}
	
	/**
	 * Shows how to sort the results
	 */
	
	void sortBySenderExample(){
		
		/* Search mails having the word 'job' in subject and return results
		   sorted by sender's email in descending order.
		 */
		SortField sortField = new SortField("sender", Type.STRING, true);	
		Sort sortBySender = new Sort(sortField);
		WildcardQuery query = new WildcardQuery(new Term("subject","job*"));
		
		try {
			System.out.println("Sorting results: Search mails having the word 'job' in subject");
			System.out.println("--- Showing results sorted by relevance");
			TopDocs topDocs = indexSearcher.search(query,20);
			printResults(topDocs);
			//Pass the sort criteria to search
			System.out.println("--- Sorting by sender names in descending order");
			topDocs = indexSearcher.search(query,null,20,sortBySender);
			printResults(topDocs);
			System.out.println("--- Sorting by the document index order");
			topDocs = indexSearcher.search(query,null,20,Sort.INDEXORDER);
			printResults(topDocs);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/*
	 * Searches for mails that have 'job' in the subject field, applies a filter
	 * to exclude mails that have sender address prefixed by word 'job' 
	 */
	
	void filterExample(){
		Term prefix = new Term("sender","jobs");
		Filter prefixFilter = new PrefixFilter(prefix);
		WildcardQuery query = new WildcardQuery(new Term("subject","job*"));
		try {
			System.out.println("Search for mails that have 'job' in the subject" +
					" field, apply a filter to exclude mails that have sender" +
					" email prefixed by 'job'");
			System.out.println("---------------------------------------------");
			System.out.println("--- Before applying prefix filter");
			TopDocs topDocs = indexSearcher.search(query,20);
			printResults(topDocs);
			System.out.println("--- After applying prefix filter");
			topDocs = indexSearcher.search(query,prefixFilter,20);
			printResults(topDocs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
    private void printResults(TopDocs topDocs)
            throws CorruptIndexException, IOException {
        for(ScoreDoc scoredoc: topDocs.scoreDocs){
            //Retrieve the matched document and show relevant details
            Document doc = indexSearcher.doc(scoredoc.doc);
            System.out.println("Sender: "+doc.getField("sender").stringValue());
        }
    }
    
    private void showSearchResults(Query query ){
        
        try{
            /* First parameter is the query to be executed and 
               second parameter indicates the no of search results to fetch
             */
            TopDocs topDocs = indexSearcher.search(query,20);   
            System.out.println("Total hits "+topDocs.totalHits);
            
            // Get an array of references to matched documents
            ScoreDoc[] scoreDosArray = topDocs.scoreDocs;   
            for(ScoreDoc scoredoc: scoreDosArray){
                //Retrieve the matched document and show relevant details
                Document doc = indexSearcher.doc(scoredoc.doc);
                System.out.println("\nSender: "+doc.getField("sender").stringValue());
                System.out.println("Subject: "+doc.getField("subject").stringValue());
                System.out.println("Email file location: "+
                                doc.getField("emailDoc").stringValue());    
            }
            System.out.println("---------------------------------------------");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
