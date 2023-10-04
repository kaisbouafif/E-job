package com.example.ejob.coreDomain;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@SuiteDisplayName("Core Domain test suite ")
@SelectPackages({
  "com.example.ejob.coreDomain"
})
@Suite
interface CoreDomainTestSuite {}
