# 3111H Project Team 23
Here is the repo for the project portion of 3111H.

| Member            	| Contact email                         	| Github                                        	| Branch Name 	| Tasks 	|
|-------------------	|---------------------------------------	|-----------------------------------------------	|-------------	|-------	|
| Hyun Joon Jeong   	| hjjeongaa@connect.ust.hk 	              | [hjjeongaa](https://github.com/hjjeongaa)     	| hjj_feature1 	| 2,4   	|
| Ryder Khoi Daniel 	| rkdaniel@connect.ust.hk                	| [ryderdaniel](https://github.com/ryderdaniel) 	| rkd_feature 	| 1,5   	|
| Yuxi Marty Sun    	| ysunbw@connect.ust.hk                 	| [ysunbw](https://github.com/ysunbw)           	| ys_feature3 	| 3,6   	|

## Screenshots of All the Tasks and Extras
| Data Reporting Tasks                                                                                                                                           	| Reccomendation Tasks                                                                                                                                         	| Other Tasks                                                                                                                           	|
|----------------------------------------------------------------------------------------------------------------------------------------------------------------	|--------------------------------------------------------------------------------------------------------------------------------------------------------------	|---------------------------------------------------------------------------------------------------------------------------------------	|
| [Data Reporting](https://github.com/hjjeongaa/3111H-project-t23/blob/master/Documentation/images/png/screenshots/datareporting/dataReportingImages.md)         	| [Baby Names](https://github.com/hjjeongaa/3111H-project-t23/blob/master/Documentation/images/png/screenshots/recommendbabyname/BabyNameImages.md)            	| [Export](https://github.com/hjjeongaa/3111H-project-t23/blob/master/Documentation/images/png/screenshots/export/ExportImage.md)       	|
| [Top Names](https://github.com/hjjeongaa/3111H-project-t23/blob/master/Documentation/images/png/screenshots/topnnames/topNNamesImages.md)                      	| [Soulmate Name](https://github.com/hjjeongaa/3111H-project-t23/blob/master/Documentation/images/png/screenshots/soulmatename/SoulmateImages.md)              	| [Settings](https://github.com/hjjeongaa/3111H-project-t23/blob/master/Documentation/images/png/screenshots/settings/SettingsImage.md) 	|
| [Name Popularity](https://github.com/hjjeongaa/3111H-project-t23/blob/master/Documentation/images/png/screenshots/popularityofname/popularityOfNamesImages.md) 	| [Name Compatibility](https://github.com/hjjeongaa/3111H-project-t23/blob/master/Documentation/images/png/screenshots/namecompatibility/NameCompatibility.md) 	|                                                                                                                                       	|
| [Name Trends](https://github.com/hjjeongaa/3111H-project-t23/blob/master/Documentation/images/png/screenshots/trendinpopularity/TrendImage.md)                 	|                                                                                                                                                              	|                                                                                                                                       	|


## Reports and Documentation
| Links                                                                                                           	|
|-----------------------------------------------------------------------------------------------------------------	|
| [Report on Unit Testing](https://github.com/hjjeongaa/3111H-project-t23/tree/master/Documentation/test)         	|
| [Report on Coverage Tests](https://github.com/hjjeongaa/3111H-project-t23/tree/master/Documentation/jacocoHTML) 	|
| [JavaDoc](https://github.com/hjjeongaa/3111H-project-t23/tree/master/Documentation/javadoc)                     	|


## Supplementary Notes
| Title                                                                                                                                                                                                  	| Tasks 	| Author            	|
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	|-------	|-------------------	|
| [Supplementary Notes](https://github.com/hjjeongaa/3111H-project-t23/blob/master/Documentation/RKD_Supplementary_Notes.md)                                                                             	| 1,5   	| Ryder Khoi Daniel 	|
| [Supplementary Notes](https://github.com/hjjeongaa/3111H-project-t23/blob/master/Documentation/Supplementary%20Notes/Supplementary%20notes%20for%20T2%2C%20T4%2C%20Export%20(Hyun%20Joon%20Jeong).pdf) 	| 2,4   	| Hyun Joon Jeong   	|
| [Supplementary Notes](https://github.com/hjjeongaa/3111H-project-t23/blob/master/Documentation/Task%203-6%20Supplementary%20Notes.pdf)                                                                 	| 3,6   	| Marty Yuxi Sun    	|

## Other Things to Note:
* Data structure of the data sets is different than the default layout. Here is more information:
```
Standard structure for data sets:

Each directory should have an updated metadata.txt
For the case of dataset directories (directories with only folders:this directory, and the human and pet directories)
	- metadata.txt should list the directory names, as spelled in the directory, on a new line for each new directory.
For the case of endpoint directories (directories containing the csv files):
	- metadata.txt's first line should have the start year of the dataset inclusive
	- the second line hsould have the end year of the dataset inclusive
	- the source on the third line
	- the member responsible for scraping should be on the fourth line.


All csv files of the dataset used by the software should be named in the following naming convention:
	- yob____.csv where ____ is the associated year.
```


