package com.target;

import java.io.ByteArrayOutputStream;
import java.io.File;
 
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
 
 
public class JGitPrintContent
{
  public static void main(String[] args) throws Exception
  {
    File gitWorkDir = new File("/Users/z001t9h/EDW");
    Git git = Git.open(gitWorkDir);
    Repository repo = git.getRepository();
 
    ObjectId lastCommitId = repo.resolve("HEAD");
 
    RevWalk revWalk = new RevWalk(repo);
    RevCommit commit = revWalk.parseCommit(lastCommitId);
 
    RevTree tree = commit.getTree();
 
    TreeWalk treeWalk = new TreeWalk(repo);
    treeWalk.addTree(tree);
    treeWalk.setRecursive(true);
    treeWalk.setFilter(PathFilter.create("file1.txt"));
    if (!treeWalk.next()) 
    {
      System.out.println("Nothing found!");
      return;
    }
 
    ObjectId objectId = treeWalk.getObjectId(0);
    ObjectLoader loader = repo.open(objectId);
 
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    loader.copyTo(out);
    System.out.println("file1.txt:\n" + out.toString());
    }
}
