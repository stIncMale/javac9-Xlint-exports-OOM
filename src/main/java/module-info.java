module stinc.male.server {
  requires transitive jsr305;
  requires com.google.common;
  requires java.dogstatsd.client;
  exports mypackage;
}