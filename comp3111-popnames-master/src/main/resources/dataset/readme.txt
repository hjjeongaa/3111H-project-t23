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