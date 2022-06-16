[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-c66648af7eb3fe8bc4f294546bfd86ef473780cde1dea487d3c4ff354943c9ae.svg)](https://classroom.github.com/online_ide?assignment_repo_id=7975750&assignment_repo_type=AssignmentRepo)
# ex4
book store
<h1>Author(s)</h1>
<ol>
<li>Nir Mualem, nirmu@edu.hac.ac.il</li>
<li>Hadas Elia, hadasm@edu.hac.ac.il</li>
</ol>

<p>Project ex4 is operating book store.
in this store admin can manage the storage and see
all orders. the user can buy books </a>.</p>


<h1>java doc</h1>
<p>src/main/java/hac/ex4/index.html</p>

<h1>log in</h1>
<p>admin username is "admin" with password "password"</p>

<h1>Assumptions</h1>
<p>
we limit the book name to 20 chars. 
</p>
<h4>
user that make a buy stay register until he log out. </br>
admin must log out user account to access /admin area. </br>
user that try go admin area get 404 error.
</h4>
<p>
when user order and the books not available he get message with
the current amount - message delete after any change.
</p>
<p>
session: the session track all user that open the store, if user close his web the session delete after amount of time that the
program decide its inactive. delete session remove the current one and open new
</p>
