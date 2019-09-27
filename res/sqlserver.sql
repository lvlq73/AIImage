-- ----------------------------
-- Table structure for ImageInfo
-- ----------------------------
DROP TABLE [dbo].[ImageInfo]
GO
CREATE TABLE [dbo].[ImageInfo] (
[imageID] int NOT NULL IDENTITY(1,1) ,
[imageCode] varchar(200) NULL ,
[imageName] varchar(200) NULL ,
[imagePath] varchar(500) NULL ,
[baiduCode] varchar(200) NULL ,
[aliyunCode] varchar(200) NULL ,
[createDate] datetime NULL
)
GO
DBCC CHECKIDENT(N'[dbo].[ImageInfo]', RESEED, 88)
GO
-- ----------------------------
-- Indexes structure for table ImageInfo
-- ----------------------------
-- ----------------------------
-- Primary Key structure for table ImageInfo
-- ----------------------------
ALTER TABLE [dbo].[ImageInfo] ADD PRIMARY KEY ([imageID])
GO
