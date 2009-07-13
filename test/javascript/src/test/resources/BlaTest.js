function BlaTest(name)
{
    TestCase.call( this, name );
}

BlaTest.prototype = new TestCase();

BlaTest.prototype.testFoo = function(){
	this.assertTrue( 5 == 5 );
}
