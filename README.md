Commentari
==========

Commentari is a Disqus like commenting system written in Java/Spring. It was inspired by Phil Leggetter of Pusher.com's Real Time Commenting System

http://coding.smashingmagazine.com/2012/05/09/building-real-time-commenting-system/

The comment system uses Javascript/Ajax to fetch, add and page through comments. Progressive enhancement is used so the system should still work if Javascript is turned off. Currently this requires an integration with your application. The demo interface uses Zurb Foundation, so adapts to tablets, mobile devices etc using HTML media calls. Comments are stored in a disk backed treap store, this is a fast binary tree. I've tested this up to a million entries and lookups are reasonable. With a front end cache this shouldn't be a big issue. You can distribute treap although generating comment ids on a distributed system is not implemented.

You will need to install the treapdb jar in your Maven repository. Look in the top level jars directory for install.sh which installs this jar and dependency.

Development is done in Spring STS/Eclipse

To Do
i. Real time commenting
ii. Caching using ehcache
iii. Redirect new comments to last page
iv. Set max number of comments, currently hardcoded as 99,999
v. Threading - probably needs an embedded Lucene
