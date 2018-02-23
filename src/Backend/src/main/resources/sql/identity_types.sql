LOCK TABLES `identity_type` WRITE;
/*!40000 ALTER TABLE `identity_type` DISABLE KEYS */;
INSERT INTO `identity_type` VALUES
('a2141e9a-b801-4565-ac4d-d21edc7be2e0','http://collinscoaches.ie/images/leapcard.jpg','2018-02-15 22:26:46','First Name:FIRSTNAME,Surname:SURNAME','http://collinscoaches.ie/images/leapcard.jpg','Leap Card','719b659b-7c22-4fd7-bb08-eb52541a4891','ACTIVE','2018-02-15 22:26:46',1),
('c219a963-7702-4874-a7c6-40fed7bfcd22','https://www.dfa.ie/media/dfa/alldfawebsitemedia/passportcitizenship/passport-landing-banner.jpg','2018-02-15 19:56:45','Firstname:FIRSTNAME,Surname:SURNAME,Birthday:BIRTHDAY','https://www.dfa.ie/media/dfa/alldfawebsitemedia/passportcitizenship/passport-landing-banner.jpg','Passport','381f0087-c9b4-492c-a5e4-c205ae0c23a5','ACTIVE','2018-02-15 19:56:45',1),
('cffe5074-f5f7-480d-ad3d-a02646ee6358','https://upload.wikimedia.org/wikipedia/en/1/1a/Dcu-logo.png','2018-02-15 22:36:14','First Name:FIRSTNAME,Surname:SURNAME,Student Number:KEY,Expiry Date:EXPIRY','https://upload.wikimedia.org/wikipedia/en/1/1a/Dcu-logo.png','DCU Student Card','8bf9bd60-f68b-48b3-8a99-32a48836b7fd','ACTIVE','2018-02-15 22:36:14',1),
('dce262e2-6694-49d3-a1d4-f4fe24f2670b','http://www.laoissc.com/wp-content/uploads/sites/4/2014/12/National-Driver-Licence-Service-Logo.png','2018-02-15 22:25:02','First Name:FIRSTNAME,Surname:SURNAME,Date of birth:BIRTHDAY,Expiry:EXPIRY','http://www.laoissc.com/wp-content/uploads/sites/4/2014/12/National-Driver-Licence-Service-Logo.png','Drivers Licence','ceb66b4e-0697-4c95-8314-4d5400621d5c','ACTIVE','2018-02-15 22:25:02',1);
/*!40000 ALTER TABLE `identity_type` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `party` WRITE;
/*!40000 ALTER TABLE `party` DISABLE KEYS */;
INSERT INTO `party` VALUES
('381f0087-c9b4-492c-a5e4-c205ae0c23a5','2018-02-13 21:26:11','Passport Authority','ACTIVE','2018-02-13 21:26:11'),
('719b659b-7c22-4fd7-bb08-eb52541a4891','2018-02-15 22:23:26','Transport for Ireland','ACTIVE','2018-02-15 22:23:26'),
('8bf9bd60-f68b-48b3-8a99-32a48836b7fd','2018-02-15 22:23:14','DCU','ACTIVE','2018-02-15 22:23:14'),
('ceb66b4e-0697-4c95-8314-4d5400621d5c','2018-02-15 22:23:06','NDLS','ACTIVE','2018-02-15 22:23:06');
/*!40000 ALTER TABLE `party` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `user_keys` WRITE;
/*!40000 ALTER TABLE `user_keys` DISABLE KEYS */;
INSERT INTO `user_keys` VALUES 
('b689d72b-eccc-44af-9121-e40980d31a14','2018-02-13 21:26:11','MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoMklebWI0ahtq+u3tA0nwd9Mckx4t/ByYQvp6+mCOuhGiGfKrHheI242UMPfJxvvIaGo8Uamhp6NlPMKyi0/545ETfSRZ4FOK18iWt70c6Igz87p1rzayYeqVdQAKoIGYyxpChmZl7g7zgwGIH9Qvh0g3t8hb1T/U6WQlbfOb+0HVqh/eWzmvs2TdTE3VMB7+4bTjBvPCuzU3h1Z/BTW/ht3ZtXLXfVVymMWvbpWzlEj9ys7c0cbztVPu1QRalXY1HTFpfhEaT50i6EXZLB1eO5DlX9e8TPUc7otTXE5OEFKUkR1H1j/degN+0sejIdV0NFW9eBRzxelMawC92upawIDAQAB','ACTIVE','2018-02-13 21:26:11','381f0087-c9b4-492c-a5e4-c205ae0c23a5',NULL),
('86af924a-1dbc-4cd8-86f1-648076db069b','2018-02-15 22:23:26','MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoMklebWI0ahtq+u3tA0nwd9Mckx4t/ByYQvp6+mCOuhGiGfKrHheI242UMPfJxvvIaGo8Uamhp6NlPMKyi0/545ETfSRZ4FOK18iWt70c6Igz87p1rzayYeqVdQAKoIGYyxpChmZl7g7zgwGIH9Qvh0g3t8hb1T/U6WQlbfOb+0HVqh/eWzmvs2TdTE3VMB7+4bTjBvPCuzU3h1Z/BTW/ht3ZtXLXfVVymMWvbpWzlEj9ys7c0cbztVPu1QRalXY1HTFpfhEaT50i6EXZLB1eO5DlX9e8TPUc7otTXE5OEFKUkR1H1j/degN+0sejIdV0NFW9eBRzxelMawC92upawIDAQAB','ACTIVE','2018-02-15 22:23:26','719b659b-7c22-4fd7-bb08-eb52541a4891',NULL),
('4b58e508-49cd-43a4-9a47-4b35a2ef0ca4','2018-02-15 22:23:14','MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoMklebWI0ahtq+u3tA0nwd9Mckx4t/ByYQvp6+mCOuhGiGfKrHheI242UMPfJxvvIaGo8Uamhp6NlPMKyi0/545ETfSRZ4FOK18iWt70c6Igz87p1rzayYeqVdQAKoIGYyxpChmZl7g7zgwGIH9Qvh0g3t8hb1T/U6WQlbfOb+0HVqh/eWzmvs2TdTE3VMB7+4bTjBvPCuzU3h1Z/BTW/ht3ZtXLXfVVymMWvbpWzlEj9ys7c0cbztVPu1QRalXY1HTFpfhEaT50i6EXZLB1eO5DlX9e8TPUc7otTXE5OEFKUkR1H1j/degN+0sejIdV0NFW9eBRzxelMawC92upawIDAQAB','ACTIVE','2018-02-15 22:23:14','8bf9bd60-f68b-48b3-8a99-32a48836b7fd',NULL),
('901e4694-bce9-4b39-9a3a-70b4ce861144','2018-02-15 22:23:06','MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoMklebWI0ahtq+u3tA0nwd9Mckx4t/ByYQvp6+mCOuhGiGfKrHheI242UMPfJxvvIaGo8Uamhp6NlPMKyi0/545ETfSRZ4FOK18iWt70c6Igz87p1rzayYeqVdQAKoIGYyxpChmZl7g7zgwGIH9Qvh0g3t8hb1T/U6WQlbfOb+0HVqh/eWzmvs2TdTE3VMB7+4bTjBvPCuzU3h1Z/BTW/ht3ZtXLXfVVymMWvbpWzlEj9ys7c0cbztVPu1QRalXY1HTFpfhEaT50i6EXZLB1eO5DlX9e8TPUc7otTXE5OEFKUkR1H1j/degN+0sejIdV0NFW9eBRzxelMawC92upawIDAQAB','ACTIVE','2018-02-15 22:23:06','ceb66b4e-0697-4c95-8314-4d5400621d5c',NULL);
/*!40000 ALTER TABLE `user_keys` ENABLE KEYS */;
UNLOCK TABLES;