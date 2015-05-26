package com.esc_project.Recommender;

import java.io.File;

import java.io.IOException;

import java.util.List;

 

import org.apache.mahout.cf.taste.common.TasteException;

import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;

import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;

import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;

import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;

import org.apache.mahout.cf.taste.model.DataModel;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import org.apache.mahout.cf.taste.similarity.ItemSimilarity;



public class ItemBasedRecommender {

 

             public List<RecommendedItem> Recommender( Long itemNumber ) {

                           try {

                                        DataModel dm = new FileDataModel(new File("data/items.csv"));

                                        ItemSimilarity sim = new LogLikelihoodSimilarity(dm);

                                        //TanimotoCoefficientSimilarity sim = new TanimotoCoefficientSimilarity(dm);

                                        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dm, sim);

                                        //전달받은 itemNumber로 5개의 추천 상품을 List에 저장한다.
                                        List<RecommendedItem>recommendedItems = recommender.mostSimilarItems( itemNumber,5);
                                        /*
                                        for(LongPrimitiveIterator items = dm.getItemIDs(); items.hasNext();) {

                                                     long itemId = items.nextLong();

                                                     List<RecommendedItem>recommendations = recommender.mostSimilarItems(itemId, 5);

                                                     for(RecommendedItem recommendation : recommendations){

                                                                  System.out.println(itemId + "," + recommendation.getItemID() + "," + recommendation.getValue());
                                                     }
                                                     x++;

                                                     //if(x > 10) System.exit(1);
                                        }
                                        //*/
                                    	return recommendedItems;

                           } catch (IOException e) {

                                        System.out.println("there was an error.");

                                        e.printStackTrace();
                                        
                                        return null;

                           } catch (TasteException e) {

                                        System.out.println("there was an Taste Exception.");

                                        e.printStackTrace();
                                        
                                        return null;
                           }
					
             }
}