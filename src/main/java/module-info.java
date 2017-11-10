/**
 * This module is not exactly correct (or rather not optimal), it should be just
 * <pre>{@code
 *  requires transitive java.dogstatsd.client;
 *  exports mypackage;
 * }</pre>
 * But that is the point, javac must tell us if it is still OK, or if something is not right. It however not only fails to tell us anything,
 * but instead throws {@link OutOfMemoryError} even when it has 6GB Java heap size available.
 */
module stinc.male.server {
  requires transitive jsr305;
  requires java.dogstatsd.client;
  exports mypackage;
}