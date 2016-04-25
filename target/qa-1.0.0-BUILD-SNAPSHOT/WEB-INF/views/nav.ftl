
<#macro navMacro>
	<nav id='nav'>
		<div id='toptab'>
			<ul id='functab'>
				<li id='logo'>
					<a class='logoa'>
						<img class='logoimg' src='../img/logo.png'>
					</a>
				</li>
				<li class='navE'>
					<a href='/articles'>我的博客</a>
				</li>

			</ul>
			
			<div class='user'>
					<div class='navB loginout' href=''>退出</div>
					<div class='navB'>欢迎您，${blogname!"隔壁老李"}~</div>
					
			</div>
		</div>
	</nav>
</#macro>

